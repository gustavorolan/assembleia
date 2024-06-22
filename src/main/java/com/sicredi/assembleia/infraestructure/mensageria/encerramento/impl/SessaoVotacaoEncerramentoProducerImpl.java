package com.sicredi.assembleia.infraestructure.mensageria.encerramento.impl;

import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoEncerramentoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoEncerramentoProducerImpl implements SessaoVotacaoEncerramentoProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.sessao_votacao_encerramento.nome}")
    private String sessaoVotacaoEncerramento;

    @Override
    public void send(SessaoVotacaoResponse sessaoVotacaoResponse) {
        rabbitTemplate.convertAndSend(sessaoVotacaoEncerramento, sessaoVotacaoResponse);
    }
}
