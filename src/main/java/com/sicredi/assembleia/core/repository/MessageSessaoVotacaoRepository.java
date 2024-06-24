package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageSessaoVotacaoRepository extends JpaRepository<MessageSessaoVotacaoEntity, Long> {

    @Query("SELECT COUNT(m) FROM MessageSessaoVotacaoEntity m WHERE m.sessaoVotacaoId = :sessaoVotacaoId")
    Long getTotalBySessaoId(@Param("sessaoVotacaoId") Long sessaoVotacaoId);
}
