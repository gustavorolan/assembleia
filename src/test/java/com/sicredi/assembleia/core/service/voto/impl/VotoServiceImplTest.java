package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.*;
import com.sicredi.assembleia.core.mapper.VotoMapper;
import com.sicredi.assembleia.core.repository.VotoRepository;
import com.sicredi.assembleia.core.service.associado.AssociadoService;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.core.service.voto.VotacaoVerfier;
import com.sicredi.assembleia.core.service.voto.VotoProducer;
import com.sicredi.assembleia.core.service.voto.VotoService;
import com.sicredi.assembleia.factory.AssociadoFactory;
import com.sicredi.assembleia.factory.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.VotoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VotoServiceImplTest {

    private final VotoRepository votoRepository = Mockito.mock(VotoRepository.class);

    private final SessaoVotacaoService sessaoVotacaoService = Mockito.mock(SessaoVotacaoService.class);

    private final VotoMapper votoMapper = new VotoMapper();

    private final AssociadoService associadoService = Mockito.mock(AssociadoService.class);

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService = Mockito.mock(SessaoVotacaoCacheService.class);

    private final List<VotacaoVerfier> votacaoVerfiers = List.of();

    private final VotoProducer votoProducer = Mockito.mock(VotoProducer.class);

    private final ZonedDateTimeService zonedDateTimeService = Mockito.mock(ZonedDateTimeService.class);

    private final VotoService votoService = new VotoServiceImpl(
            votoRepository,
            sessaoVotacaoService,
            votoMapper,
            associadoService,
            sessaoVotacaoCacheService,
            votacaoVerfiers,
            votoProducer,
            zonedDateTimeService
    );

    @Captor
    private ArgumentCaptor<VotoEntity> votoEntityCaptor;

    @Test
    @DisplayName("Deve inserir voto request na fila e no cache")
    void deveInserirVotoRequestNaFilaENoCache() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();

        Mockito.when(sessaoVotacaoCacheService.findById(votoRequest.getSessaoId()))
                .thenReturn(sessaoVotacaoCacheEntity);

        votoService.votar(votoRequest);

        Mockito.verify(votoProducer, Mockito.times(1)).send(votoRequest);

        Mockito.verify(sessaoVotacaoCacheService, Mockito.times(1))
                .inserirVotoNaSessaoVotacaoEmCache(votoRequest);
    }

    @Test
    @DisplayName("Deve inserir consumir voto request da fila voto a favor")
    void deveInserirVotoRequestDaFilaVotoAFavor() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoEntity sessaoVotacaoEntityEsperado = SessaoVotacaoFactory.entidadeBuilder()
                .votosFavor(21)
                .total(51)
                .build();
        VotoEntity votoEntityEsperado = VotoFactory.entidadeBuilder()
                .id(null)
                .sessao(sessaoVotacaoEntityEsperado)
                .build();
        AssociadoEntity associadoEntity = AssociadoFactory.criarEntity();

        Mockito.when(sessaoVotacaoService.findById(votoRequest.getSessaoId()))
                .thenReturn(sessaoVotacaoEntity);
        Mockito.when(associadoService.findOrInsert(votoRequest.getCpf()))
                .thenReturn(associadoEntity);

        votoService.consume(votoRequest);

        Mockito.verify(votoRepository, Mockito.times(1))
                .save(votoEntityCaptor.capture());
        Mockito.verify(associadoService, Mockito.times(1))
                .findOrInsert(votoRequest.getCpf());
        Mockito.verify(sessaoVotacaoService, Mockito.times(1))
                .findById(votoRequest.getSessaoId());

        Assertions.assertEquals(votoEntityEsperado, votoEntityCaptor.getValue());

        Mockito.verifyNoMoreInteractions(votoRepository, associadoService, sessaoVotacaoService);
    }

    @Test
    @DisplayName("Deve inserir consumir voto request da fila voto contra")
    void deveInserirVotoRequestDaFilaVotoContra() {
        VotoRequest votoRequest = VotoFactory.requestBuilder()
                .voto(VotoEnum.NAO)
                .build();
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoEntity sessaoVotacaoEntityEsperado = SessaoVotacaoFactory.entidadeBuilder()
                .votosContra(31)
                .total(51)
                .build();
        VotoEntity votoEntityEsperado = VotoFactory.entidadeBuilder()
                .id(null)
                .votoEnum(VotoEnum.NAO)
                .sessao(sessaoVotacaoEntityEsperado)
                .build();
        AssociadoEntity associadoEntity = AssociadoFactory.criarEntity();

        Mockito.when(sessaoVotacaoService.findById(votoRequest.getSessaoId()))
                .thenReturn(sessaoVotacaoEntity);
        Mockito.when(associadoService.findOrInsert(votoRequest.getCpf()))
                .thenReturn(associadoEntity);

        votoService.consume(votoRequest);

        Mockito.verify(votoRepository, Mockito.times(1))
                .save(votoEntityCaptor.capture());
        Mockito.verify(associadoService, Mockito.times(1))
                .findOrInsert(votoRequest.getCpf());
        Mockito.verify(sessaoVotacaoService, Mockito.times(1))
                .findById(votoRequest.getSessaoId());

        Assertions.assertEquals(votoEntityEsperado, votoEntityCaptor.getValue());

        Mockito.verifyNoMoreInteractions(votoRepository, associadoService, sessaoVotacaoService);
    }
}