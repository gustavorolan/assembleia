package com.sicredi.assembleia.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AberturaSessaoVotacaoRequest {
    @NotNull(message = "Id da pauta não pode ser nulo!")
    @Schema(description = "Pauta Id", example = "1")
    private Long pautaId;

    @Schema(description = "Duração em minutos do tempo de duração da sessão", example = "1")
    private Integer duracaoMinutos = 1;
}