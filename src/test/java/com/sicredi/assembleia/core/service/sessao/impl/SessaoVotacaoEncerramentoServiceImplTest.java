package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.factory.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.ZonedDateTimeFactory;
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

    @Mock
    private SessaoVotacaoService sessaoVotacaoService;

    @Mock
    private SessaoVotacaoCacheService sessaoVotacaoCacheService;

    @Mock
    private ZonedDateTimeService zonedDateTimeService;

    @InjectMocks
    private SessaoVotacaoEncerramentoServiceImpl sessaoVotacaoEncerramentoService;

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

        Mockito.verifyNoMoreInteractions(sessaoVotacaoService, zonedDateTimeService, sessaoVotacaoCacheService);
    }
}