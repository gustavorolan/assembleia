package com.sicredi.assembleia.core.mapper;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.AssociadoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.VotoEntity;
import org.springframework.stereotype.Component;

@Component
public class VotoMapper {
    public VotoEntity createEntity(VotoRequest votoRequest, SessaoVotacaoEntity sessaoVotacaoEntity, AssociadoEntity associadoEntity) {
        return VotoEntity.builder()
                .sessao(sessaoVotacaoEntity)
                .associado(associadoEntity)
                .votoEnum(votoRequest.getVoto())
                .build();
    }
}
