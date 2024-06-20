package com.sicredi.assembleia.core.exception;

public class SessaoVotacaoEncerradaException extends NotFoundException {
    public SessaoVotacaoEncerradaException() {
        super("Sessao foi encerrada!");
    }
}