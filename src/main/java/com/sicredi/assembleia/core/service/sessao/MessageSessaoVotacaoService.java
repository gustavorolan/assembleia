package com.sicredi.assembleia.core.service.sessao;

import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;

public interface MessageSessaoVotacaoService {
    void aumentarNumeroDeVotosEmUm(Long sessaoId);

    MessageSessaoVotacaoEntity findBySessaoId(Long sessaoId);
}
