package com.sicredi.assembleia.factory.service;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;

public class SessaoVotacaoFactory {
    public static SessaoVotacaoEntity.SessaoVotacaoEntityBuilder entidadeBuilder() {
        return SessaoVotacaoEntity.builder()
                .id(1L)
                .total(50)
                .pauta(PautaFactory.criarEntity())
                .status(SessaoVotacaoEnum.ABERTA)
                .votosFavor(20)
                .votosContra(30)
                .horaEncerramento(ZonedDateTimeFactory.criarEncerramento())
                .horaAbertura(ZonedDateTimeFactory.criarAbertaura())
                .duracao(60);
    }

    public static SessaoVotacaoEntity criarEntidade() {
        return entidadeBuilder().build();
    }

    public static SessaoVotacaoCacheEntity.SessaoVotacaoCacheEntityBuilder entidadeCacheBuilder() {
        return SessaoVotacaoCacheEntity.builder()
                .id(1L)
                .pautaId(1L)
                .ttl(86400L)
                .horaEncerramento(ZonedDateTimeFactory.criarEncerramento())
                .horaAbertura(ZonedDateTimeFactory.criarAbertaura());
    }

    public static SessaoVotacaoCacheEntity criarEntidadeCache() {
        return entidadeCacheBuilder().build();
    }

    public static SessaoVotacaoResponse criarResponse() {
        return responseBuilder()
                .build();
    }

    public static SessaoVotacaoResponse.SessaoVotacaoResponseBuilder responseBuilder() {
        return SessaoVotacaoResponse.builder()
                .id(1L)
                .total(50)
                .pautaId(1L)
                .duracao(60)
                .horaAbertura(ZonedDateTimeFactory.criarAbertaura())
                .horaEncerramento(ZonedDateTimeFactory.criarEncerramento())
                .votosFavor(20)
                .votosContra(30)
                .status(SessaoVotacaoEnum.ABERTA);
    }

    public static AberturaSessaoVotacaoRequest criarRequestAbertura() {
        return requestAberturaBuilder()
                .build();
    }

    public static AberturaSessaoVotacaoRequest.AberturaSessaoVotacaoRequestBuilder requestAberturaBuilder() {
        return AberturaSessaoVotacaoRequest.builder()
                .pautaId(1L)
                .duracaoMinutos(60);
    }

    public static MessageSessaoVotacaoEntity createMessageSessaoVotacaoEntity() {
        return messageSessaoVotacaoEnityBuilder()
                .build();
    }

    public static MessageSessaoVotacaoEntity.MessageSessaoVotacaoEntityBuilder messageSessaoVotacaoEnityBuilder() {
        return MessageSessaoVotacaoEntity.builder()
                .sessaoVotacaoId(1L)
                .id(1L);
    }
}
