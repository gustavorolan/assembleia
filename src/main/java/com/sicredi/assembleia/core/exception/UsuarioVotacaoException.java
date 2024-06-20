package com.sicredi.assembleia.core.exception;

public class UsuarioVotacaoException extends NotFoundException {
    public UsuarioVotacaoException() {
        super("Usuario já votou!");
    }
}