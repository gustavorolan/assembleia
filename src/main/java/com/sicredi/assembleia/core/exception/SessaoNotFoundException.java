package com.sicredi.assembleia.core.exception;

public class SessaoNotFoundException extends NotFoundException {
    public SessaoNotFoundException() {
        super("Sessao não foi Encontrada!");
    }
}
