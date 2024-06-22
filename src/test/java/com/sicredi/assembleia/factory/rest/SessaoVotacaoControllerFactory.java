package com.sicredi.assembleia.factory.rest;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import com.sicredi.assembleia.rest.SessaoVotacaoController;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

public class SessaoVotacaoControllerFactory {

    public static SessaoVotacaoResponse deveAbrirUmaSessaoComSucesso(SessaoVotacaoController sessaoVotacaoController, Long pautaId) {

        int votosEsperados = 0;
        SessaoVotacaoEnum statusInicial = SessaoVotacaoEnum.ABERTA;
        AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest = SessaoVotacaoFactory.requestAberturaBuilder()
                .pautaId(pautaId)
                .duracaoMinutos(1)
                .build();

        ResponseEntity<SessaoVotacaoResponse> sessaoVotacaoResponse = sessaoVotacaoController.abrir(aberturaSessaoVotacaoRequest);
        SessaoVotacaoResponse sessaoVotacaoResponseBody = Objects.requireNonNull(sessaoVotacaoResponse.getBody());

        Assertions.assertInstanceOf(Long.class, sessaoVotacaoResponseBody.getId());
        Assertions.assertEquals(aberturaSessaoVotacaoRequest.getDuracaoMinutos(), sessaoVotacaoResponseBody.getDuracao());
        Assertions.assertEquals(aberturaSessaoVotacaoRequest.getPautaId(), sessaoVotacaoResponseBody.getPautaId());
        Assertions.assertEquals(
                aberturaSessaoVotacaoRequest.getDuracaoMinutos(),
                tempoEntreDoisZonedTimes(sessaoVotacaoResponseBody.getHoraEncerramento(), sessaoVotacaoResponseBody.getHoraAbertura())
        );
        Assertions.assertEquals(votosEsperados, sessaoVotacaoResponseBody.getVotosContra());
        Assertions.assertEquals(votosEsperados, sessaoVotacaoResponseBody.getVotosFavor());
        Assertions.assertEquals(votosEsperados, sessaoVotacaoResponseBody.getTotal());
        Assertions.assertEquals(statusInicial, sessaoVotacaoResponseBody.getStatus());

        return sessaoVotacaoResponse.getBody();
    }

    private static Integer tempoEntreDoisZonedTimes(ZonedDateTime fim, ZonedDateTime inicio) {
        Instant fimInstant = fim.toInstant();
        Instant inicioInstant = inicio.toInstant();

        return Math.toIntExact(Duration.between(inicioInstant, fimInstant).toMinutes());
    }

}