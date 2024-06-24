package com.sicredi.assembleia.core.service.sessao;

public interface MessageSessaoVotacaoService {
    void aumentarNumeroDeVotosEmUm(Long sessaoId);

    Long getTotalBySessaoId(Long sessaoId);
}
