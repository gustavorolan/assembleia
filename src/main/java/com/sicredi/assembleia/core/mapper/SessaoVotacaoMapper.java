package com.sicredi.assembleia.core.mapper;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class SessaoVotacaoMapper {

    public SessaoVotacaoResponse sessaoEntityToResponse(SessaoVotacaoEntity entity) {
        return SessaoVotacaoResponse.builder()
                .id(entity.getId())
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

    public SessaoVotacaoEntity criarNovaSessao(PautaEntity pautaEntity, int duracaoMinutos, ZonedDateTime now) {

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

    public SessaoVotacaoCacheEntity criarNovaSessaoCache(SessaoVotacaoEntity entity, Long ttl) {

        return SessaoVotacaoCacheEntity.builder()
                .pautaId(entity.getPauta().getId())
                .id(entity.getId())
                .horaAbertura(entity.getHoraAbertura())
                .horaEncerramento(entity.getHoraEncerramento())
                // Conversão para minutos
                .ttl(ttl)
                .build();
    }

    public MessageSessaoVotacaoEntity criarMessageSessaoVotacaoEntity(Long sessaoId) {
        return MessageSessaoVotacaoEntity.builder().sessaoVotacaoId(sessaoId).total(0).build();
    }

    public SessaoVotacaoCacheEntity sessaoVotacaoCacheTosessaoVotacaoCacheEntity(SessaoVotacaoEntity sessaoVotacaoEntity, Long ttl){
        return SessaoVotacaoCacheEntity.builder()
                .horaAbertura(sessaoVotacaoEntity.getHoraAbertura())
                .horaEncerramento(sessaoVotacaoEntity.getHoraEncerramento())
                .pautaId(sessaoVotacaoEntity.getPauta().getId())
                .id(sessaoVotacaoEntity.getId())
                .ttl(ttl)
                .build();
    }
}
