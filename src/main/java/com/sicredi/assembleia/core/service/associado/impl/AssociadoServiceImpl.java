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
    public AssociadoEntity upsert(String cpf) {

        AssociadoEntity associadoEntity = associadoRepository.findByCpf(cpf)
                .orElseGet(() -> AssociadoEntity.builder().cpf(cpf).build());

        return associadoRepository.save(associadoEntity);
    }
}
