package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoEncerramentoProducer;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoEncerramentoService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.service.ZonedDateTimeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoEncerramentoServiceImplTest {

    private final SessaoVotacaoService sessaoVotacaoService = Mockito.mock(SessaoVotacaoService.class);

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService = Mockito.mock(SessaoVotacaoCacheService.class);

    private final ZonedDateTimeService zonedDateTimeService = Mockito.mock(ZonedDateTimeService.class);

    private final SessaoVotacaoEncerramentoProducer sessaoVotacaoEncerramentoProducer = Mockito.mock(SessaoVotacaoEncerramentoProducer.class);

    private final SessaoVotacaoMapper sessaoVotacaoMapper = new SessaoVotacaoMapper();

    private final SessaoVotacaoEncerramentoService sessaoVotacaoEncerramentoService =
            new SessaoVotacaoEncerramentoServiceImpl(
                    sessaoVotacaoService,
                    sessaoVotacaoCacheService,
                    zonedDateTimeService,
                    sessaoVotacaoMapper,
                    sessaoVotacaoEncerramentoProducer
            );

    @Captor
    private ArgumentCaptor<SessaoVotacaoEntity> sessaoVotacaoCaptor;

    @Test
    @DisplayName("Deve encerrar sessão corretamente.")
    void deveEncerrarSessaoCorretamente() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();
        ZonedDateTime zonedDateTime = ZonedDateTimeFactory.criarForaDoEncerramentoEAbertura();

        Mockito.when(sessaoVotacaoService.findAllStatusAberto()).thenReturn(List.of(sessaoVotacaoEntity));
        Mockito.when(sessaoVotacaoCacheService.findById(sessaoVotacaoCacheEntity.getId())).thenReturn(sessaoVotacaoCacheEntity);
        Mockito.when(zonedDateTimeService.now()).thenReturn(zonedDateTime);

        sessaoVotacaoEncerramentoService.encerrar();

        Mockito.verify(sessaoVotacaoService, Mockito.times(1)).findAllStatusAberto();
        Mockito.verify(sessaoVotacaoCacheService, Mockito.times(1)).findById(sessaoVotacaoCacheEntity.getId());
        Mockito.verify(zonedDateTimeService, Mockito.times(1)).now();
        Mockito.verify(sessaoVotacaoService, Mockito.times(1)).save(sessaoVotacaoCaptor.capture());
        Mockito.verify(sessaoVotacaoCacheService, Mockito.times(1)).delete(sessaoVotacaoCacheEntity);
        Mockito.verify(sessaoVotacaoEncerramentoProducer,Mockito.times(1))
                .send(Mockito.any(SessaoVotacaoResponse.class));

        Assertions.assertEquals(SessaoVotacaoEnum.ENCERRADA, sessaoVotacaoCaptor.getValue().getStatus());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoService, zonedDateTimeService, sessaoVotacaoCacheService);
    }

    @Test
    @DisplayName("Não Deve encerrar sessão.")
    void naoDeveEncerrarSessao() {
        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();
        ZonedDateTime zonedDateTime = ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura();

        Mockito.when(sessaoVotacaoService.findAllStatusAberto()).thenReturn(List.of(sessaoVotacaoEntity));
        Mockito.when(sessaoVotacaoCacheService.findById(sessaoVotacaoCacheEntity.getId())).thenReturn(sessaoVotacaoCacheEntity);
        Mockito.when(zonedDateTimeService.now()).thenReturn(zonedDateTime);

        sessaoVotacaoEncerramentoService.encerrar();

        Mockito.verify(sessaoVotacaoService, Mockito.times(1)).findAllStatusAberto();
        Mockito.verify(sessaoVotacaoCacheService, Mockito.times(1)).findById(sessaoVotacaoCacheEntity.getId());
        Mockito.verify(zonedDateTimeService, Mockito.times(1)).now();
        Mockito.verify(sessaoVotacaoService, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(sessaoVotacaoEncerramentoProducer,Mockito.times(0))
                .send(Mockito.any(SessaoVotacaoResponse.class));

        Mockito.verifyNoMoreInteractions(sessaoVotacaoService, zonedDateTimeService, sessaoVotacaoCacheService);
    }
}