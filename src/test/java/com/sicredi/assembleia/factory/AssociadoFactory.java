package com.sicredi.assembleia.factory;

import com.sicredi.assembleia.core.entity.AssociadoEntity;

public class AssociadoFactory {
    public static AssociadoEntity criarEntity() {
        return entityBuilder().build();
    }

    public static AssociadoEntity.AssociadoEntityBuilder entityBuilder() {
        return AssociadoEntity.builder()
                .cpf("00011122233");
    }
}
