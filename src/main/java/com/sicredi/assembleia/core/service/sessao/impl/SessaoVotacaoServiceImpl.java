package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.exception.PautaJaTemUmaSessaoVotacaoException;
import com.sicredi.assembleia.core.exception.SessaoNotFoundException;
import com.sicredi.assembleia.core.repository.SessaoVotacaoRepository;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import com.sicredi.assembleia.core.service.pauta.PautaService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoServiceImpl.class);

    private static final int TAMANHO_PAGINACAO = 10;

    private static final String PROPRIEDADE_PARA_ORDENCAO = "id";

    private static final Sort.Direction DIRECAO_ORDERNACAO = Sort.Direction.DESC;

    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    private final PautaService pautaService;

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService;

    private final ZonedDateTimeService zonedDateTimeService;

    @Override
    @Transactional
    public SessaoVotacaoResponse abrir(AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest) {
        logger.info("Iniciando processo de abertura da sessão de votação");

        PautaEntity pautaEntity = pautaService.findById(aberturaSessaoVotacaoRequest.getPautaId());

        SessaoVotacaoEntity sessaoVotacaoEntity = sessaoVotacaoMapper.criarNovaSessao(
                pautaEntity,
                aberturaSessaoVotacaoRequest.getDuracaoMinutos(),
                zonedDateTimeService.now()
        );

        SessaoVotacaoEntity sessaoVotacaoSaved = save(sessaoVotacaoEntity);

        sessaoVotacaoCacheService.inserirSessaoVotacaoEmCache(sessaoVotacaoSaved);

        logger.info("Sessão de votação foi aberta com sucesso. sessaoId: {}", sessaoVotacaoSaved.getId());
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
    public List<SessaoVotacaoEntity> findAllStatusAberto() {
        logger.info("Consultando todas sessões abertas.");
        return sessaoVotacaoRepository.findAllByStatus(SessaoVotacaoEnum.ABERTA);
    }

    @Override
    public SessaoVotacaoEntity save(SessaoVotacaoEntity entity) {
        try {
            return sessaoVotacaoRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage(), e);
            throw new PautaJaTemUmaSessaoVotacaoException();
        }
    }

    @Override
    public Page<SessaoVotacaoResponse> findAllByStatus(int page, SessaoVotacaoEnum status) {
        return sessaoVotacaoRepository.findAllByStatus(status, pageRequest(page))
                .map(sessaoVotacaoMapper::sessaoEntityToResponse);
    }

    private PageRequest pageRequest(int page) {
        return PageRequest.of(page,TAMANHO_PAGINACAO, DIRECAO_ORDERNACAO,PROPRIEDADE_PARA_ORDENCAO);
    }
}
