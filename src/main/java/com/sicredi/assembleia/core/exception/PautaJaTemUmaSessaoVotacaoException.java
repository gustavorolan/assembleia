package com.sicredi.assembleia.core.exception;

public class PautaJaTemUmaSessaoVotacaoException extends BadRequestException {
    public PautaJaTemUmaSessaoVotacaoException() {
        super("Já existe uma sessão para essa pauta!");
    }
}