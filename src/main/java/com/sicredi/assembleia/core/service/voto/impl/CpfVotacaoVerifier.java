package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.exception.CpfInvalidoException;
import com.sicredi.assembleia.core.service.voto.VotacaoVerfier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class CpfVotacaoVerifier implements VotacaoVerfier {

    private static final String UNFORMATTED_CPF_REGEX = "^\\d{11}$";

    @Override
    public void verify(VotoRequest votoRequest, SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity, ZonedDateTime now) {
        Pattern pattern = Pattern.compile(UNFORMATTED_CPF_REGEX);
        Matcher matcher = pattern.matcher(votoRequest.getCpf());
        if(!matcher.matches()) throw new CpfInvalidoException();
    }
}
