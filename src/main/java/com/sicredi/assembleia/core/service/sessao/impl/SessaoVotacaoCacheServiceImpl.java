package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.exception.SessaoCacheNotFoundException;
import com.sicredi.assembleia.core.repository.SessaoVotacaoCacheRepository;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoCacheServiceImpl implements SessaoVotacaoCacheService {

    private static final Logger logger = LoggerFactory.getLogger(SessaoVotacaoCacheServiceImpl.class);

    private final SessaoVotacaoCacheRepository sessaoVotacaoCacheRepository;

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    @Value("${spring.data.redis.sessao_votacao_cache.ttl}")
    private String sessaoCacheTtl;


    @Override
    public SessaoVotacaoCacheEntity inserirSessaoVotacaoEmCache(SessaoVotacaoEntity sessaoVotacaoEntity) {
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = sessaoVotacaoMapper.criarNovaSessaoCache(sessaoVotacaoEntity, Long.parseLong(sessaoCacheTtl));
        sessaoVotacaoCacheRepository.save(sessaoVotacaoCacheEntity);
        logger.info("Sessão votação cache adicionado com sucesso!");
        return sessaoVotacaoCacheEntity;
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
    public void delete(Long id){
        sessaoVotacaoCacheRepository.deleteById(id);
    }
}
