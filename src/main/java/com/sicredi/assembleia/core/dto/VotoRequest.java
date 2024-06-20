package com.sicredi.assembleia.core.dto;

import com.sicredi.assembleia.core.entity.VotoEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequest {
    private VotoEnum voto;
    private Long sessaoId;
    private String cpf;
}
