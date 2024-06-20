package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.exception.SessaoNotFoundException;
import com.sicredi.assembleia.core.repository.SessaoVotacaoRepository;
import com.sicredi.assembleia.core.service.pauta.PautaService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoServiceImpl.class);

    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    private final PautaService pautaService;

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    private final SessaoVotacaoCacheService  sessaoVotacaoCacheService;

    @Override
    public SessaoVotacaoResponse abrir(AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest) {
        PautaEntity pautaEntity = pautaService.findById(aberturaSessaoVotacaoRequest.getPautaId());

        SessaoVotacaoEntity sessaoVotacaoEntity = sessaoVotacaoMapper
                .criarNovaSessao(pautaEntity, aberturaSessaoVotacaoRequest.getDuracaoMinutos());

        SessaoVotacaoEntity sessaoVotacaoSaved = sessaoVotacaoRepository.save(sessaoVotacaoEntity);

        sessaoVotacaoCacheService.inserirVotoEmCache(sessaoVotacaoEntity);

        return sessaoVotacaoMapper.sessaoEntityToResponse(sessaoVotacaoSaved);
    }

    @Override
    public SessaoVotacaoEntity findById(Long id) {

        SessaoNotFoundException sessaoNotFoundException = new SessaoNotFoundException();

        logger.info("Consultando uma sessao com o id: {}", id);

        return sessaoVotacaoRepository.findById(id).orElseThrow(() -> {
                    logger.error(sessaoNotFoundException.getMessage(), sessaoNotFoundException);
                    return sessaoNotFoundException;
                }
        );
    }

    @Override
    public SessaoVotacaoResponse findResponseById(Long id) {
        SessaoVotacaoEntity sessaoVotacaoEntity = findById(id);
        return sessaoVotacaoMapper.sessaoEntityToResponse(sessaoVotacaoEntity);
    }

    @Override
    public List<SessaoVotacaoEntity> findAllStatusAberto(){
        return sessaoVotacaoRepository.findAllByStatus(SessaoVotacaoEnum.ABERTA);
    }

    @Override
    public SessaoVotacaoEntity save (SessaoVotacaoEntity entity){
        return sessaoVotacaoRepository.save(entity);
    }
}
