package com.sicredi.assembleia.core.dto;

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
    private Long pautaId;

    private Integer duracaoMinutos = 1;
}