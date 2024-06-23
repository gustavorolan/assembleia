package com.sicredi.assembleia.core.mapper;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import org.springframework.stereotype.Component;

@Component
public class PautaMapper {
    public PautaEntity pauteDtoToEntity(PautaRequest dto) {
        return PautaEntity.builder()
                .name(dto.getNome())
                .descricao(dto.getDescricao())
                .build();
    }

    public PautaResponse pautaEntityToResponse(PautaEntity entity) {
        return PautaResponse.builder()
                .descricao(entity.getDescricao())
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
