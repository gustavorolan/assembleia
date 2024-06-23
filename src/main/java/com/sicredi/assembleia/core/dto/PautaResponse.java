package com.sicredi.assembleia.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaResponse {
    @Schema(description = "Pauta Id", example = "1")
    private Long id;
    @Schema(description = "Nome da pauta", example = "Nome")
    private String name;
    @Schema(description = "Descrição da pauta", example = "Desc")
    private String descricao;
}