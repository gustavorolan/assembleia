package com.sicredi.assembleia.core.exception;

public class SessaoCacheNotFoundException extends NotFoundException {
    public SessaoCacheNotFoundException() {
        super("Sessão não foi encontrada em cache!");
    }
}
