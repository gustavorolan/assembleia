package com.sicredi.assembleia.core.dto;

import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class SessaoVotacaoResponse {
    @Schema(description = "Id da sessão de votação", example = "1")
    private Long id;
    @Schema(description = "Horário de abertura", example = "2024-06-20T20:32:46.812331Z")
    private ZonedDateTime horaAbertura;
    @Schema(description = "Horário de encerramento", example = "2024-06-20T20:32:46.812331Z")
    private ZonedDateTime horaEncerramento;
    @Schema(description = "Tempo de Duração em minutos", example = "1")
    private Integer duracao;
    @Schema(description = "Pauta Id", example = "1")
    private Long pautaId;
    @Schema(description = "Quantidade de votos favor", example = "250")
    private Integer votosFavor;
    @Schema(description = "Quantidade de votos contra", example = "250")
    private Integer votosContra;
    @Schema(description = "Aberta ou Encerrada, sessão é encerrada após processar todos votos", example = "250")
    private SessaoVotacaoEnum status;
    @Schema(description = "Quantidade total de votos", example = "500")
    private Integer total;
}