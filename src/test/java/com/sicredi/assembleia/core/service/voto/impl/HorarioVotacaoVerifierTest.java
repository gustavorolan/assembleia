package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.SessaoVotacaoEncerradaException;
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
class HorarioVotacaoVerifierTest {

    @InjectMocks
    private HorarioVotacaoVerifier horarioVotacaoVerifier;

    @Test
    @DisplayName("Deve lançar uma exceção pois sessão já foi encerrada.")
    void deveLancarExcecaoPoisSessaoFoiEncerrada() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();

        Assertions.assertThrowsExactly(SessaoVotacaoEncerradaException.class, () ->
                horarioVotacaoVerifier.verify(
                        votoRequest,
                        sessaoVotacaoCacheEntity,
                        ZonedDateTimeFactory.criarForaDoEncerramentoEAbertura()
                )
        );
    }

    @Test
    @DisplayName("Não deve lançar uma exceção pois sessão está aberta.")
    void naoDeveLancarExcecaoPoisSessaoEstaAberta() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();

        horarioVotacaoVerifier.verify(
                votoRequest,
                sessaoVotacaoCacheEntity,
                ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura()
        );
    }
}