package com.sicredi.assembleia.core.service.sessao;


import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;

public interface SessaoVotacaoCacheService {

    void inserirVotoEmCache(SessaoVotacaoEntity sessaoVotacaoEntity);

    SessaoVotacaoCacheEntity findById(Long sessaoVotacaoId);

    void inserirVotoEmCache(VotoRequest votoRequest);
}
