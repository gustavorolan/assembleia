package com.sicredi.assembleia.factory;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.VotoEntity;
import com.sicredi.assembleia.core.entity.VotoEnum;

public class VotoFactory {
    public static VotoEntity criarEntidade(){

        return entidadeBuilder()
                .build();
    }

    public static VotoEntity.VotoEntityBuilder entidadeBuilder() {
        return VotoEntity.builder()
                .sessao(SessaoVotacaoFactory.criarEntidade())
                .associado(AssociadoFactory.criarEntity())
                .votoEnum(VotoEnum.SIM)
                .id(1L);
    }

    public static VotoRequest criarRequest(){
        return requestBuilder()
                .build();
    }

    public static VotoRequest.VotoRequestBuilder requestBuilder() {
        return VotoRequest.builder()
                .voto(VotoEnum.SIM)
                .cpf(AssociadoFactory.criarEntity().getCpf())
                .sessaoId(1L);
    }
}
