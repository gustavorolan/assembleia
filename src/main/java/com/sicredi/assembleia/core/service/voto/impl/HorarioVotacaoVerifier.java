package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.SessaoVotacaoEncerradaException;
import com.sicredi.assembleia.core.service.voto.VotacaoVerfier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class HorarioVotacaoVerifier implements VotacaoVerfier {

    @Override
    public void verify(VotoRequest votoRequest, SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity, ZonedDateTime now) {
        if (!now.isAfter(sessaoVotacaoCacheEntity.getHoraAbertura()) ||
                !now.isBefore(sessaoVotacaoCacheEntity.getHoraEncerramento()))
            throw new SessaoVotacaoEncerradaException();
    }
}
