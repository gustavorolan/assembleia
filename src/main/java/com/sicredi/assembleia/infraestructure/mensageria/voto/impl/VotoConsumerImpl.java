package com.sicredi.assembleia.infraestructure.mensageria.voto.impl;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.service.voto.VotoService;
import com.sicredi.assembleia.core.service.voto.VotoConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotoConsumerImpl implements VotoConsumer {

    private final VotoService votoService;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queue.voto.nome}")
    public void consume(VotoRequest votoRequest) {
        votoService.consume(votoRequest);
    }
}