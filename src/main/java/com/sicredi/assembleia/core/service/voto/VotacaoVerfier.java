package com.sicredi.assembleia.core.service.voto;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;

public interface VotacaoVerfier {
    public void verify(VotoRequest votoRequest, SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity);
}
