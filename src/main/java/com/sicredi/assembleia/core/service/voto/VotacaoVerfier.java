package com.sicredi.assembleia.core.service.voto;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;

import java.time.ZonedDateTime;

public interface VotacaoVerfier {
    void verify(
            VotoRequest votoRequest,
            SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity,
            ZonedDateTime now,
            boolean isAssociadoTentandoVotarNovamente
    );
}
