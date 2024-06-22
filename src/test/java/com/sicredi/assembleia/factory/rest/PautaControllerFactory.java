package com.sicredi.assembleia.factory.rest;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.factory.service.PautaFactory;
import com.sicredi.assembleia.rest.PautaController;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;

public class PautaControllerFactory {

    public static PautaResponse deveCriarUmaPautaComSucesso(PautaController pautaController) {
        PautaRequest pautaRequest = PautaFactory.criarRequest();
        ResponseEntity<Long> criarPautaReponse = pautaController.criar(pautaRequest);
        ResponseEntity<PautaResponse> findByIdPautaResponse = pautaController.findById(criarPautaReponse.getBody());

        Assertions.assertInstanceOf(PautaResponse.class, findByIdPautaResponse.getBody());
        Assertions.assertInstanceOf(Long.class, criarPautaReponse.getBody());
        Assertions.assertEquals(findByIdPautaResponse.getBody().getDescricao(), pautaRequest.getDescricao());
        Assertions.assertEquals(findByIdPautaResponse.getBody().getName(), pautaRequest.getNome());
        Assertions.assertInstanceOf(Long.class, findByIdPautaResponse.getBody().getId());

        return findByIdPautaResponse.getBody();
    }

}