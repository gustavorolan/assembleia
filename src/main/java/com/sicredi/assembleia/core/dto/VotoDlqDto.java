package com.sicredi.assembleia.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoDlqDto {
    private VotoRequest votoRequest;
    private Exception exception;
}
