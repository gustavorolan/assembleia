package com.sicredi.assembleia.core.service.sessao;


import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;

public interface SessaoVotacaoCacheService {

    SessaoVotacaoCacheEntity inserirSessaoVotacaoEmCache(SessaoVotacaoEntity sessaoVotacaoEntity);

    SessaoVotacaoCacheEntity findById(Long sessaoVotacaoId);

    void delete(Long id);
}
