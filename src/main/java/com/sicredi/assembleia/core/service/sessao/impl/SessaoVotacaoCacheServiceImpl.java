package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.exception.SessaoCacheNotFoundException;
import com.sicredi.assembleia.core.repository.SessaoVotacaoCacheRepository;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SessaoVotacaoCacheServiceImpl implements SessaoVotacaoCacheService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoCacheServiceImpl.class);

    private final SessaoVotacaoCacheRepository sessaoVotacaoCacheRepository;

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    @Override
    public void inserirSessaoVotacaoEmCache(SessaoVotacaoEntity sessaoVotacaoEntity) {
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = sessaoVotacaoMapper.criarNovaSessaoCache(sessaoVotacaoEntity);
        sessaoVotacaoCacheRepository.save(sessaoVotacaoCacheEntity);
        logger.info("Sessão votação cache adicionado com sucesso!");
    }


    @Override
    public SessaoVotacaoCacheEntity findById(Long sessaoVotacaoId) {
        logger.info("Consultando sessão votação cache com id: {}", sessaoVotacaoId);
        return sessaoVotacaoCacheRepository.findById(sessaoVotacaoId).orElseThrow(() -> {
            SessaoCacheNotFoundException exception = new SessaoCacheNotFoundException();
            logger.error(exception.getMessage(), exception);
            return exception;
        });
    }

    @Override
    public void inserirVotoNaSessaoVotacaoEmCache(VotoRequest votoRequest) {
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = findById(votoRequest.getSessaoId());

        String cpf = votoRequest.getCpf();
        List<String> associadosIds = sessaoVotacaoCacheEntity.getAssociadosCpfs();

        if (associadosIds == null)
            sessaoVotacaoCacheEntity.setAssociadosCpfs(List.of(cpf));
        else
            sessaoVotacaoCacheEntity.getAssociadosCpfs().add(cpf);

        atualizarNumeroTotalDevotos(sessaoVotacaoCacheEntity);

        sessaoVotacaoCacheRepository.save(sessaoVotacaoCacheEntity);

        logger.info("Voto adicionado à sessão votação cache com sucesso!");
    }

    private static void atualizarNumeroTotalDevotos(SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity) {
        sessaoVotacaoCacheEntity.setTotal(sessaoVotacaoCacheEntity.getTotal() + 1);
    }
}
