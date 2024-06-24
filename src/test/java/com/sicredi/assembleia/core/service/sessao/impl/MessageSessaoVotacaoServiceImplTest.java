package com.sicredi.assembleia.core.service.sessao.impl;


import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
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
        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder()
                .id(null)
                .build();

        Mockito.when(messageSessaoVotacaoRepository.save(Mockito.any(MessageSessaoVotacaoEntity.class))).thenReturn(entityEsperada);

        messageSessaoVotacaoService.aumentarNumeroDeVotosEmUm(entity.getSessaoVotacaoId());

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).save(captor.capture());

        Assertions.assertEquals(entityEsperada, captor.getValue());

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve aumentar o número de votos em um corretamente")
    void deveAumentarNumeroDeVotosEmUmCorretamenteECriarUmaEntidadeNova() {

        MessageSessaoVotacaoEntity entityInicial = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder().id(null).build();

        MessageSessaoVotacaoEntity entityEsperada = SessaoVotacaoFactory.messageSessaoVotacaoEnityBuilder().id(null).build();

        Mockito.when(messageSessaoVotacaoRepository.save(Mockito.any(MessageSessaoVotacaoEntity.class))).thenReturn(entityInicial);

        messageSessaoVotacaoService.aumentarNumeroDeVotosEmUm(entityInicial.getSessaoVotacaoId());

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).save(captor.capture());

        Assertions.assertEquals(entityEsperada, captor.getValue());

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

    @Test
    @DisplayName("Deve contar entidade pelo id")
    void deveContaEntidadePeloId() {
        Long sessaoId = 1L;
        long totalEsperado = 50L;

        Mockito.when(messageSessaoVotacaoRepository.getTotalBySessaoId(sessaoId)).thenReturn(totalEsperado);

        Long response = messageSessaoVotacaoService.getTotalBySessaoId(sessaoId);

        Mockito.verify(messageSessaoVotacaoRepository, Mockito.times(1)).getTotalBySessaoId(sessaoId);

        Assertions.assertEquals(totalEsperado, response);

        Mockito.verifyNoMoreInteractions(messageSessaoVotacaoRepository);
    }

}