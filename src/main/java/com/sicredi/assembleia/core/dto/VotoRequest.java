package com.sicredi.assembleia.core.dto;

import com.sicredi.assembleia.core.entity.VotoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequest {
    @Schema(description = "Voto pode ser SIM ou NAO", example = "SIM")
    private VotoEnum voto;
    @Schema(description = "Sessão que você deseja votar", example = "1")
    private Long sessaoId;
    @Schema(description = "Cpf da pessoa que deseja votar", example = "00011122233")
    private String cpf;
}
