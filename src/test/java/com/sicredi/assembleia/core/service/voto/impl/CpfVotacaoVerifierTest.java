package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.CpfInvalidoException;
import com.sicredi.assembleia.factory.SessaoVotacaoFactory;
import com.sicredi.assembleia.factory.VotoFactory;
import com.sicredi.assembleia.factory.ZonedDateTimeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CpfVotacaoVerifierTest {

    @InjectMocks
    private CpfVotacaoVerifier cpfVotacaoVerifier;

    @Test
    @DisplayName("Deve lançar exceção pois cpd está no formato incorreto")
    void deveLancarExcecaoCpfErrado() {
        VotoRequest votoRequest = VotoFactory.requestBuilder()
                .cpf("21321as")
                .build();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();

        Assertions.assertThrowsExactly(CpfInvalidoException.class, () ->
                cpfVotacaoVerifier.verify(
                        votoRequest,
                        sessaoVotacaoCacheEntity,
                        ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura()
                )
        );
    }

    @Test
    @DisplayName("Não Deve lançar exceção pois cpf está no formato correto")
    void naoDeveLancarExcecaoCpfCorreto() {
        VotoRequest votoRequest = VotoFactory.criarRequest();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();
        cpfVotacaoVerifier.verify(
                votoRequest,
                sessaoVotacaoCacheEntity,
                ZonedDateTimeFactory.criarDataEntreEncerramentoEAbertura()
        );
    }
}