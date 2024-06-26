package com.sicredi.assembleia.infraestructure.mensageria.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue.voto.nome}")
    private String votoQueue;

    @Value("${spring.rabbitmq.queue.voto.dlq}")
    private String votoQueueDlq;

    @Value("${spring.rabbitmq.queue.sessao_votacao_encerramento.nome}")
    private String sessaoVotacaoEncerramento;

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue votoQueue() {
        return new Queue(votoQueue);
    }

    @Bean
    public Queue votoQueueDlq() {
        return new Queue(votoQueueDlq);
    }

    @Bean
    public Queue sessaoVotacaoEncerramentoQueue() {
        return new Queue(sessaoVotacaoEncerramento);
    }
}