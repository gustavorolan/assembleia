package com.sicredi.assembleia.core.exception;

public class UsuarioVotacaoException extends BadRequestException {
    public UsuarioVotacaoException() {
        super("Usuario já votou!");
    }
}