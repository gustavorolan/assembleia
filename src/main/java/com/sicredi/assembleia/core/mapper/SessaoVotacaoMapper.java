package com.sicredi.assembleia.core.mapper;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.*;
import com.sicredi.assembleia.core.factory.DateTimeFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
public class SessaoVotacaoMapper {

    public static final int MINUTOS_PARA_SEGUNDOS = 60;

    public SessaoVotacaoResponse sessaoEntityToResponse(SessaoVotacaoEntity entity) {
        return SessaoVotacaoResponse.builder()
                .pautaId(entity.getId())
                .duracao(entity.getDuracao())
                .horaAbertura(entity.getHoraAbertura())
                .horaEncerramento(entity.getHoraEncerramento())
                .votosContra(entity.getVotosContra())
                .votosFavor(entity.getVotosFavor())
                .pautaId(entity.getPauta().getId())
                .status(entity.getStatus())
                .total(entity.getTotal())
                .build();
    }

    public SessaoVotacaoEntity criarNovaSessao(PautaEntity pautaEntity, int duracaoMinutos) {
        ZonedDateTime now = DateTimeFactory.now();

        return SessaoVotacaoEntity.builder()
                .pauta(pautaEntity)
                .duracao(duracaoMinutos)
                .horaAbertura(now)
                .horaEncerramento(now.plusMinutes(duracaoMinutos))
                .votosContra(0)
                .votosFavor(0)
                .total(0)
                .status(SessaoVotacaoEnum.ABERTA)
                .build();
    }

    public SessaoVotacaoCacheEntity criarNovaSessaoCache(SessaoVotacaoEntity entity) {

        return SessaoVotacaoCacheEntity.builder()
                .pautaId(entity.getPauta().getId())
                .id(entity.getId())
                .associadosCpfs(new ArrayList<>())
                .horaAbertura(entity.getHoraAbertura())
                .total(0)
                .horaEncerramento(entity.getHoraEncerramento())
                // Conversão para minutos
                .ttl(86400L)
                .build();
    }
}
