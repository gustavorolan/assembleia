package com.sicredi.assembleia.infraestructure.mensageria.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.service.voto.VotoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoProducerImpl implements VotoProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue.voto.nome}")
    private String votoQueue;

    @Override
    public void send(VotoRequest votoRequest) {
        rabbitTemplate.convertAndSend(votoQueue, votoRequest);
    }
}
