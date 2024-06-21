package com.sicredi.assembleia.core.exception;

public class SessaoVotacaoEncerradaException extends BadRequestException {
    public SessaoVotacaoEncerradaException() {
        super("Sessao foi encerrada!");
    }
}