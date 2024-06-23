package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.UsuarioVotacaoException;
import com.sicredi.assembleia.core.service.voto.VotacaoVerfier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UsuarioVotacaoVerifier implements VotacaoVerfier {

    @Override
    public void verify(VotoRequest votoRequest, SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity, ZonedDateTime now) {
        Set<Long> associadosCpfs = sessaoVotacaoCacheEntity.getAssociadosCpfs();
        if (associadosCpfs != null && associadosCpfs.contains(Long.parseLong(votoRequest.getCpf())))
            throw new UsuarioVotacaoException();
    }
}
