package com.sicredi.assembleia.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaResponse {
    private Long id;
    private String name;
    private String descricao;
}