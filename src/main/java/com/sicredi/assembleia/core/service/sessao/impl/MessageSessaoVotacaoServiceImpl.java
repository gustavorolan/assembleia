package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.entity.MessageSessaoVotacaoEntity;
import com.sicredi.assembleia.core.exception.MessageSessaoNotFoundException;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.repository.MessageSessaoVotacaoRepository;
import com.sicredi.assembleia.core.service.sessao.MessageSessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSessaoVotacaoServiceImpl implements MessageSessaoVotacaoService {

    private static final Logger logger = LoggerFactory.getLogger(MessageSessaoVotacaoServiceImpl.class);

    private final SessaoVotacaoMapper sessaoVotacaoMapper;

    private final MessageSessaoVotacaoRepository messageSessaoVotacaoRepository;

    @Override
    public void aumentarNumeroDeVotosEmUm(Long sessaoId) {
        MessageSessaoVotacaoEntity messageSessaoVotacaoEntity = messageSessaoVotacaoRepository.findBySessaoVotacaoId(sessaoId)
                .orElse(sessaoVotacaoMapper.criarMessageSessaoVotacaoEntity(sessaoId));

        messageSessaoVotacaoEntity.setTotal(messageSessaoVotacaoEntity.getTotal() + 1);

        messageSessaoVotacaoRepository.save(messageSessaoVotacaoEntity);
    }

    @Override
    public MessageSessaoVotacaoEntity findBySessaoId(Long sessaoId) {

        MessageSessaoNotFoundException exception = new MessageSessaoNotFoundException();

        return messageSessaoVotacaoRepository.findBySessaoVotacaoId(sessaoId)
                .orElseThrow(() -> {
                    logger.error(exception.getMessage(), exception);
                    return exception;
                }
        );
    }
}
