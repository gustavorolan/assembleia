package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.exception.PautaJaTemUmaSessaoVotacaoException;
import com.sicredi.assembleia.core.exception.SessaoNotFoundException;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.repository.SessaoVotacaoRepository;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.pauta.PautaService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.factory.service.PautaFactory;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.service.ZonedDateTimeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

class SessaoVotacaoServiceImplTest {

    private final SessaoVotacaoRepository sessaoVotacaoRepository = Mockito.mock(SessaoVotacaoRepository.class);

    private final PautaService pautaService = Mockito.mock(PautaService.class);

    private final SessaoVotacaoMapper sessaoVotacaoMapper = new SessaoVotacaoMapper();

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService = Mockito.mock(SessaoVotacaoCacheService.class);

    private final ZonedDateTimeService zonedDateTimeService = Mockito.mock(ZonedDateTimeService.class);

    private final SessaoVotacaoService sessaoVotacaoService = new SessaoVotacaoServiceImpl(
            sessaoVotacaoRepository,
            pautaService,
            sessaoVotacaoMapper,
            sessaoVotacaoCacheService,
            zonedDateTimeService
    );

    @Test
    @DisplayName("Deve salvar entidade corretamente.")
    void deveSalvarEntidadeCorretamente() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();

        Mockito.when(sessaoVotacaoRepository.save(sessaoVotacaoEntity)).thenReturn(sessaoVotacaoEntity);

        SessaoVotacaoEntity response = sessaoVotacaoService.save(sessaoVotacaoEntity);

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1)).save(sessaoVotacaoEntity);

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);

        Assertions.assertEquals(sessaoVotacaoEntity, response);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar entidade.")
    void deveLancarExcecaoAoSalvarEntidade() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();

        Mockito.when(sessaoVotacaoRepository.save(sessaoVotacaoEntity)).thenThrow(new DataIntegrityViolationException(""));

        Assertions.assertThrowsExactly(PautaJaTemUmaSessaoVotacaoException.class, () ->
                sessaoVotacaoService.save(sessaoVotacaoEntity)
        );

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1)).save(sessaoVotacaoEntity);

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve retornar lista com sessões abertas.")
    void deveRetornarListaComSessoesAbertas() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();

        Mockito.when(sessaoVotacaoRepository.findAllByStatus(SessaoVotacaoEnum.ABERTA))
                .thenReturn(List.of(sessaoVotacaoEntity));

        List<SessaoVotacaoEntity> response = sessaoVotacaoService.findAllStatusAberto();

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1))
                .findAllByStatus(SessaoVotacaoEnum.ABERTA);

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);

        Assertions.assertEquals(List.of(sessaoVotacaoEntity), response);
    }

    @Test
    @DisplayName("Deve retornar sessão corretamente pelo id.")
    void deveRetornarSessaoCorretamentePeloId() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();

        Mockito.when(sessaoVotacaoRepository.findById(sessaoVotacaoEntity.getId()))
                .thenReturn(Optional.of(sessaoVotacaoEntity));

        SessaoVotacaoEntity response = sessaoVotacaoService.findById(sessaoVotacaoEntity.getId());

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1))
                .findById(sessaoVotacaoEntity.getId());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);

        Assertions.assertEquals(sessaoVotacaoEntity, response);
    }

    @Test
    @DisplayName("Deve lançar exceção ao retornar sessão pelo id.")
    void deveLancarExcecaoAoRetornarSessaoPeloId() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();

        Mockito.when(sessaoVotacaoRepository.findById(sessaoVotacaoEntity.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(SessaoNotFoundException.class, () ->
                sessaoVotacaoService.findById(sessaoVotacaoEntity.getId())
        );

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1))
                .findById(sessaoVotacaoEntity.getId());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve retornar sessão response corretamente pelo id.")
    void deveRetornarSessaoResponseCorretamentePeloId() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoResponse sessaoVotacaoResponse = SessaoVotacaoFactory.criarResponse();

        Mockito.when(sessaoVotacaoRepository.findById(sessaoVotacaoEntity.getId()))
                .thenReturn(Optional.of(sessaoVotacaoEntity));

        SessaoVotacaoResponse response = sessaoVotacaoService.findResponseById(sessaoVotacaoEntity.getId());

        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1))
                .findById(sessaoVotacaoEntity.getId());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoRepository);

        Assertions.assertEquals(sessaoVotacaoResponse, response);
    }


    @Test
    @DisplayName("Deve abrir uma sessao corretamente.")
    void deveAbrirUmaSessaoCorretamente() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.entidadeBuilder()
                .votosFavor(0)
                .votosContra(0)
                .total(0)
                .build();

        SessaoVotacaoEntity sessaoVotacaoEntityEsperadaParaSerSalva = SessaoVotacaoFactory.entidadeBuilder()
                .id(null)
                .votosFavor(0)
                .votosContra(0)
                .total(0)
                .build();

        SessaoVotacaoResponse sessaoVotacaoResponse = SessaoVotacaoFactory.responseBuilder()
                .votosFavor(0)
                .votosContra(0)
                .total(0)
                .build();

        AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest = SessaoVotacaoFactory.criarRequestAbertura();
        PautaEntity pautaEntity = PautaFactory.criarEntity();
        ZonedDateTime now = ZonedDateTimeFactory.criarAbertaura();

        Mockito.when(pautaService.findById(aberturaSessaoVotacaoRequest.getPautaId()))
                .thenReturn(pautaEntity);

        Mockito.when(sessaoVotacaoRepository.save(Mockito.any(SessaoVotacaoEntity.class)))
                .thenReturn(sessaoVotacaoEntity);

        Mockito.when(zonedDateTimeService.now()).thenReturn(now);

        SessaoVotacaoResponse response = sessaoVotacaoService.abrir(aberturaSessaoVotacaoRequest);

        Mockito.verify(sessaoVotacaoCacheService, Mockito.times(1))
                .inserirSessaoVotacaoEmCache(sessaoVotacaoEntity);
        Mockito.verify(sessaoVotacaoRepository, Mockito.times(1))
                .save(sessaoVotacaoEntityEsperadaParaSerSalva);
        Mockito.verify(pautaService, Mockito.times(1))
                .findById(aberturaSessaoVotacaoRequest.getPautaId());

        Mockito.verify(zonedDateTimeService, Mockito.times(1))
                .now();

        Mockito.verifyNoMoreInteractions(
                sessaoVotacaoRepository,
                pautaService,
                sessaoVotacaoCacheService,
                zonedDateTimeService
        );

        Assertions.assertEquals(sessaoVotacaoResponse, response);
    }

}