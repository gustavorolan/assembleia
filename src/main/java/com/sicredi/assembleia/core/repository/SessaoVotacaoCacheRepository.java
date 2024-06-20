package com.sicredi.assembleia.core.repository;

import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoVotacaoCacheRepository extends CrudRepository<SessaoVotacaoCacheEntity, Long> {

}
