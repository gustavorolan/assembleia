package com.sicredi.assembleia.core.service.sessao;


import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;

public interface SessaoVotacaoCacheService {

    void inserirSessaoVotacaoEmCache(SessaoVotacaoEntity sessaoVotacaoEntity);

    SessaoVotacaoCacheEntity findById(Long sessaoVotacaoId);

    void inserirVotoNaSessaoVotacaoEmCache(VotoRequest votoRequest);

    void delete(SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity);
}
