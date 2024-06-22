package com.sicredi.assembleia.core.service.associado;

import com.sicredi.assembleia.core.entity.AssociadoEntity;

public interface AssociadoService {
    AssociadoEntity findOrInsert(String cpf);
}
