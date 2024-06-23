package com.sicredi.assembleia.infraestructure.mensageria.voto.impl;

import com.sicredi.assembleia.core.dto.VotoDlqDto;
import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.service.sessao.impl.SessaoVotacaoServiceImpl;
import com.sicredi.assembleia.core.service.voto.VotoService;
import com.sicredi.assembleia.core.service.voto.VotoConsumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoConsumerImpl implements VotoConsumer {

    private static final Logger logger = LoggerFactory.getLogger(VotoConsumerImpl.class);

    private final VotoService votoService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.voto.dlq}")
    private String votoQueueDlq;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queue.voto.nome}")
    public void consume(VotoRequest votoRequest) {

        try {

            votoService.consume(votoRequest);

        } catch (Exception e) {

            VotoDlqDto votoDlqDto = VotoDlqDto.builder()
                    .exception(e)
                    .votoRequest(votoRequest)
                    .build();

            rabbitTemplate.convertAndSend(votoQueueDlq, votoDlqDto);

            logger.error("Mensagem enviada para dlq: ", e);
        }

    }
}