package com.sicredi.assembleia.core.service.sessao;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;

public interface SessaoVotacaoEncerramentoProducer {
    void send(SessaoVotacaoResponse sessaoVotacaoResponse);
}
