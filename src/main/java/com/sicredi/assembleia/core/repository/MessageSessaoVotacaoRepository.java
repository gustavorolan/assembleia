package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageSessaoVotacaoRepository extends JpaRepository<MessageSessaoVotacaoEntity, Long> {

    Optional<MessageSessaoVotacaoEntity> findBySessaoVotacaoId(Long sessaoVotacaoId);
}
