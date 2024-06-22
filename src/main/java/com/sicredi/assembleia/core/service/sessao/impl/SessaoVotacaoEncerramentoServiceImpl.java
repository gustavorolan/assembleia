package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoEncerramentoService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoEncerramentoServiceImpl implements SessaoVotacaoEncerramentoService {

    private final SessaoVotacaoService sessaoVotacaoService;

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService;

    private final ZonedDateTimeService zonedDateTimeService;

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoEncerramentoServiceImpl.class);

    @Override
    @Scheduled(fixedRateString = "${spring.sessao_votacao.encerramento.tempo.ms}")
    public void encerrar() {
        logger.info("Iniciando processo de encerramento de sessões");

        List<SessaoVotacaoEntity> sessoes = sessaoVotacaoService.findAllStatusAberto();

        sessoes.forEach(this::encerrar);

        logger.info("Terminando processo de encerramento de sessões");
    }

    @Async
    protected void encerrar(SessaoVotacaoEntity sessao) {
        SessaoVotacaoCacheEntity sessaoCache = sessaoVotacaoCacheService.findById(sessao.getId());

        ZonedDateTime now = zonedDateTimeService.now();

        boolean isTodasMensagensProcessadas = sessaoCache.getTotal() != null && sessaoCache.getTotal() >= sessao.getTotal();
        boolean isHorarioEncerrado = now.isAfter(sessao.getHoraEncerramento());

        if (isTodasMensagensProcessadas && isHorarioEncerrado) {
            sessao.setStatus(SessaoVotacaoEnum.ENCERRADA);
            sessaoVotacaoService.save(sessao);
            sessaoVotacaoCacheService.delete(sessaoCache);
            logger.info("Sessao encerrada, id: {}", sessao.getId());
        }

    }

}
