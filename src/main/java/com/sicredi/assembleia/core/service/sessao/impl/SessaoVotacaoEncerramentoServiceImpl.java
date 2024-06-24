package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.*;
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

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoEncerramentoServiceImpl.class);

    private final SessaoVotacaoService sessaoVotacaoService;

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService;

    private final ZonedDateTimeService zonedDateTimeService;

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    private final SessaoVotacaoEncerramentoProducer sessaoVotacaoEncerramentoProducer;

    private final MessageSessaoVotacaoService messageSessaoVotacaoService;

    @Override
    @Scheduled(fixedRateString = "${spring.sessao_votacao.encerramento.tempo.ms}")
    @Async
    public void encerrar() {
        logger.info("Iniciando processo de encerramento de sessões");

        List<SessaoVotacaoEntity> sessoes = sessaoVotacaoService.findAllStatusAberto();

        sessoes.forEach(this::encerrar);

        logger.info("Terminando processo de encerramento de sessões");
    }


    @Async
    void encerrar(SessaoVotacaoEntity sessao) {

        MessageSessaoVotacaoEntity messageSessaoVotacaoEntity = messageSessaoVotacaoService
                .findBySessaoId(sessao.getId());

        ZonedDateTime now = zonedDateTimeService.now();
        boolean isHorarioEncerrado = now.isAfter(sessao.getHoraEncerramento());

        boolean isTodasMensagensProcessadas = messageSessaoVotacaoEntity.getTotal() <= sessao.getTotal();

        if (isHorarioEncerrado && isTodasMensagensProcessadas) {
            sessao.setStatus(SessaoVotacaoEnum.ENCERRADA);
            sessaoVotacaoService.save(sessao);
            sessaoVotacaoCacheService.delete(sessao.getId());
            logger.info("Sessao encerrada, id: {}", sessao.getId());
            SessaoVotacaoResponse sessaoVotacaoResponse = sessaoVotacaoMapper.sessaoEntityToResponse(sessao);
            sessaoVotacaoEncerramentoProducer.send(sessaoVotacaoResponse);
            logger.info("Mensagem enviada para fila de encerramento, id: {}", sessao.getId());
        }

    }

}
