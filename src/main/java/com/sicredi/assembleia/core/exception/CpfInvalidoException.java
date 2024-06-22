package com.sicredi.assembleia.core.exception;

public class CpfInvalidoException extends BadRequestException {
    public CpfInvalidoException() {
        super("Cpf inválido!");
    }
}