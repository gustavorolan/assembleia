package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.UsuarioVotacaoException;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.service.VotoFactory;
import com.sicredi.assembleia.factory.service.ZonedDateTimeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioVotacaoVerifierTest {

    @InjectMocks
    private UsuarioVotacaoVerifier usuarioVotacaoVerifier;

    @Test
    @DisplayName("Deve lançar uma exceção pois usuário já votou.")
    void deveLancarExcecaoPoisUsarioJaVotou() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.entidadeCacheBuilder()
                .build();
        boolean isAssociadoTentandoVotarNovamente = true;

        Assertions.assertThrowsExactly(UsuarioVotacaoException.class, () ->
                usuarioVotacaoVerifier.verify(
                        votoRequest,
                        sessaoVotacaoCacheEntity,
                        ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura(),
                        isAssociadoTentandoVotarNovamente
                )
        );
    }

    @Test
    @DisplayName("Não deve lançar uma exceção pois usuário não votou ainda.")
    void naoDeveLancarExcecaoPoisUsarioNaoVotou() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.entidadeCacheBuilder()
                .build();
        boolean isAssociadoTentandoVotarNovamente = false;

        usuarioVotacaoVerifier.verify(
                votoRequest,
                sessaoVotacaoCacheEntity,
                ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura(),
                isAssociadoTentandoVotarNovamente
        );
    }
}