package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.VotoEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<VotoEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM VotoEntity v " +
            "INNER JOIN v.sessao s  " +
            "INNER JOIN v.associado a " +
            "WHERE s.id = :id AND a.cpf = :cpf")
    boolean isAssociadoTentandoVotarNovamente(@Param("id") Long id, @Param("cpf") String cpf);
}
