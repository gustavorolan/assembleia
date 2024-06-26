package com.sicredi.assembleia.core.service.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.*;
import com.sicredi.assembleia.core.exception.SessaoCacheNotFoundException;
import com.sicredi.assembleia.core.repository.VotoRepository;
import com.sicredi.assembleia.core.service.associado.AssociadoService;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.sessao.MessageSessaoVotacaoService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.core.service.sessao.impl.SessaoVotacaoCacheServiceImpl;
import com.sicredi.assembleia.core.service.voto.VotoProducer;
import com.sicredi.assembleia.core.service.voto.VotoService;
import com.sicredi.assembleia.core.service.voto.VotacaoVerfier;
import com.sicredi.assembleia.core.mapper.VotoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoCacheServiceImpl.class);

    private final VotoRepository votoRepository;

    private final SessaoVotacaoService sessaoVotacaoService;

    private final VotoMapper votoMapper;

    private final AssociadoService associadoService;

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService;

    private final List<VotacaoVerfier> votacaoVerfiers;

    private final VotoProducer votoProducer;

    private final ZonedDateTimeService zonedDateTimeService;

    private final MessageSessaoVotacaoService messageSessaoVotacaoService;

    @Override
    @Transactional
    public void votar(VotoRequest votoRequest) {

        SessaoVotacaoCacheEntity sessaoVotacaoCache = obterSessaoVotacaoCache(votoRequest);

        boolean isAssociadoTentandoVotarNovamente = votoRepository
                .isAssociadoTentandoVotarNovamente(votoRequest.getSessaoId(), votoRequest.getCpf());

        verify(votoRequest, sessaoVotacaoCache, isAssociadoTentandoVotarNovamente);

        votoProducer.send(votoRequest);

        messageSessaoVotacaoService.aumentarNumeroDeVotosEmUm(votoRequest.getSessaoId());

        logger.info("Voto foi adicionado na fila para processamento sessaoVotacao: {}", votoRequest.getSessaoId());
    }

    @Override
    @Transactional
    public void consume(VotoRequest votoRequest) {
        SessaoVotacaoEntity sessaoVotacaoEntity = sessaoVotacaoService.findById(votoRequest.getSessaoId());

        AssociadoEntity associadoEntity = associadoService.findOrInsert(votoRequest.getCpf());

        logger.info("Inicio do processamento da mensagem de votação. sessaoVotacao: {} usuarioId: {}",
                votoRequest.getSessaoId(), associadoEntity.getId());

        aumentaContadorDeVotos(votoRequest, sessaoVotacaoEntity);

        VotoEntity votoEntity = votoMapper.createEntity(votoRequest, sessaoVotacaoEntity, associadoEntity);

        votoRepository.save(votoEntity);

        logger.info("Mensagem de votação foi processado com sucesso. sessaoVotacao: {} usuarioId: {}",
                votoRequest.getSessaoId(), associadoEntity.getId());
    }

    private void aumentaContadorDeVotos(VotoRequest votoRequest, SessaoVotacaoEntity sessaoVotacaoEntity) {

        sessaoVotacaoEntity.setTotal(sessaoVotacaoEntity.getTotal() + 1);

        if (votoRequest.getVoto() == VotoEnum.SIM)
            sessaoVotacaoEntity.setVotosFavor(sessaoVotacaoEntity.getVotosFavor() + 1);
        else sessaoVotacaoEntity.setVotosContra(sessaoVotacaoEntity.getVotosContra() + 1);
    }


    private void verify(VotoRequest votoRequest, SessaoVotacaoCacheEntity sessaoVotacaoCache, boolean isAssociadoTentandoVotarNovamente) {
        votacaoVerfiers.forEach(verifier -> {
            verifier.verify(
                    votoRequest,
                    sessaoVotacaoCache,
                    zonedDateTimeService.now(),
                    isAssociadoTentandoVotarNovamente
            );
        });
    }

    private SessaoVotacaoCacheEntity obterSessaoVotacaoCache(VotoRequest votoRequest) {
        try {
            return sessaoVotacaoCacheService.findById(votoRequest.getSessaoId());
        } catch (SessaoCacheNotFoundException sessaoCacheNotFoundException) {
            return sessaoVotacaoService.inserirSessaoVotacaoCacheEntity(votoRequest.getSessaoId());
        }
    }

}
