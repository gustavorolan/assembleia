package com.sicredi.assembleia.factory.rest;

import com.sicredi.assembleia.core.dto.VotoRequest;

import com.sicredi.assembleia.core.entity.VotoEnum;
import com.sicredi.assembleia.factory.service.VotoFactory;
import com.sicredi.assembleia.rest.VotoController;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VotoControllerFactory {
    public static void deveConseguirEfetuarVotoFavorSessaoAberta(VotoController votoController, Long sessaoId, String cpf) {
        VotoRequest votoRequest = VotoFactory.requestBuilder()
                .sessaoId(sessaoId)
                .cpf(cpf)
                .build();

        ResponseEntity<Object> response = votoController.votar(votoRequest);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    public static void deveConseguirEfetuarVotoContraSessaoAberta(VotoController votoController, Long sessaoId, String cpf) {
        VotoRequest votoRequest = VotoFactory.requestBuilder()
                .voto(VotoEnum.NAO)
                .cpf(cpf)
                .sessaoId(sessaoId)
                .build();

        ResponseEntity<Object> response = votoController.votar(votoRequest);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}
