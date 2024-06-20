package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.AssociadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<AssociadoEntity, Long> {
    Optional<AssociadoEntity> findByCpf(String cpf);
}
