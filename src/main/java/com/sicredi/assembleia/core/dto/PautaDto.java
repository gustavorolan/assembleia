package com.sicredi.assembleia.core.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PautaDto {
    @NotNull(message = "Nome da pauta não pode ser nulo!")
    private String nome;

    @NotNull(message = "Descrição da pauta não pode ser nulo!")
    private String descricao;
}