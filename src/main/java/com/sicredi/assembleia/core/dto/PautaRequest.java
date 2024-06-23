package com.sicredi.assembleia.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaRequest {
    @NotNull(message = "Nome da pauta não pode ser nulo!")
    @Schema(description = "Nome para pauta", example = "Nome")
    private String nome;

    @NotNull(message = "Descrição da pauta não pode ser nulo!")
    @Schema(description = "Descrição para pauta", example = "Desc")
    private String descricao;
}