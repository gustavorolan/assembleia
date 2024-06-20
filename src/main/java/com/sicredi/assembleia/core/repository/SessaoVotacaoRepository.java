package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacaoEntity, Long> {
    List<SessaoVotacaoEntity> findAllByStatus(SessaoVotacaoEnum status);
}
