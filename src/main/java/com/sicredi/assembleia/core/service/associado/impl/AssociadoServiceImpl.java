package com.sicredi.assembleia.core.service.associado.impl;

import com.sicredi.assembleia.core.entity.AssociadoEntity;
import com.sicredi.assembleia.core.repository.AssociadoRepository;
import com.sicredi.assembleia.core.service.associado.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AssociadoServiceImpl implements AssociadoService {

    private final AssociadoRepository associadoRepository;

    @Override
    public AssociadoEntity findOrInsert(String cpf) {
        return associadoRepository.findByCpf(cpf)
                .orElseGet(() -> createNewEntity(cpf));
    }

    private AssociadoEntity createNewEntity(String cpf) {
        AssociadoEntity entity = AssociadoEntity.builder().cpf(cpf).build();
        return associadoRepository.save(entity);
    }
}
