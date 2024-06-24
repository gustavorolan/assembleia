package com.sicredi.assembleia.core.service.sessao.impl;


import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import com.sicredi.assembleia.core.exception.MessageSessaoNotFoundException;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.repository.MessageSessaoVotacaoRepository;
import com.sicredi.assembleia.core.service.sessao.MessageSessaoVotacaoService;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MessageSessaoVotacaoServiceImplTest {

    private final SessaoVotacaoMapper sessaoVotacaoMapper = new SessaoVotacaoMapper();

    private final MessageSessaoVotacaoRepository messageSessaoVotacaoRepository = Mockito.mock(MessageSessaoVotacaoRepository.class);

    private final MessageSessaoVotacaoService messageSessaoVotacaoService = new MessageSessaoVotacaoServiceImpl(sessaoVotacaoMapper, messageSessaoVotacaoRepository);

    @Captor
    private ArgumentCaptor<MessageSessaoVotacaoEntity> captor;

    @Test
    @DisplayName("Deve aumentar o número de votos em um corretamente")
    void deveAumentarNumeroDeVotosEmUmCorretamente() {
        MessageSessaoVotacaoEntity entity = SessaoVotacaoFactory.createMessageSessaoVotacaoEntity();
        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder().total(51).build();

        Mockito.when(messageSessaoVotacaoRepository.findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId())).thenReturn(Optional.of(entity));
        Mockito.when(messageSessaoVotacaoRepository.save(Mockito.any(MessageSessaoVotacaoEntity.class))).thenReturn(entityEsperada);

        messageSessaoVotacaoService.aumentarNumeroDeVotosEmUm(entity.getSessaoVotacaoId());

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).save(captor.capture());
        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).findBySessaoVotacaoId(entity.getSessaoVotacaoId());

        Assertions.assertEquals(entityEsperada, captor.getValue());

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve aumentar o número de votos em um corretamente e criar uma entidade nova")
    void deveAumentarNumeroDeVotosEmUmCorretamenteECriarUmaEntidadeNova() {

        MessageSessaoVotacaoEntity entityInicial = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder().id(null).total(0).build();

        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder().id(null).total(1).build();

        Mockito.when(messageSessaoVotacaoRepository.findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId())).thenReturn(Optional.empty());
        Mockito.when(messageSessaoVotacaoRepository.save(Mockito.any(MessageSessaoVotacaoEntity.class))).thenReturn(entityInicial);

        messageSessaoVotacaoService.aumentarNumeroDeVotosEmUm(entityInicial.getSessaoVotacaoId());

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).save(captor.capture());
        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).findBySessaoVotacaoId(entityInicial.getSessaoVotacaoId());

        Assertions.assertEquals(entityEsperada, captor.getValue());

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve encontrar entidade pelo id")
    void deveEncontrarEntidadePeloId() {

        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.createMessageSessaoVotacaoEntity();

        Mockito.when(messageSessaoVotacaoRepository.findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId())).thenReturn(Optional.of(entityEsperada));

        MessageSessaoVotacaoEntity response = messageSessaoVotacaoService.findBySessaoId(entityEsperada.getSessaoVotacaoId());

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId());

        Assertions.assertEquals(entityEsperada, response);

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar encontrar entidade pelo id")
    void deveLancarExcecaoAOtentarEncontrarEntidadePeloSessaoVotacaoId() {

        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.createMessageSessaoVotacaoEntity();

        Mockito.when(messageSessaoVotacaoRepository.findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId())).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(MessageSessaoNotFoundException.class, () ->
                messageSessaoVotacaoService.findBySessaoId(entityEsperada.getSessaoVotacaoId()));

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).findBySessaoVotacaoId(entityEsperada.getSessaoVotacaoId());

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }
}