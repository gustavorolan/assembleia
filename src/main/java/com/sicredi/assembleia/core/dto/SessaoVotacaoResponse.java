package com.sicredi.assembleia.core.dto;

import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class SessaoVotacaoResponse {
    private ZonedDateTime horaAbertura;
    private ZonedDateTime horaEncerramento;
    private Integer duracao;
    private Long pautaId;
    private Integer votosFavor;
    private Integer votosContra;
    private SessaoVotacaoEnum status;
    private Integer total;
}