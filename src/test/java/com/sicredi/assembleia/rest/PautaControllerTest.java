package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.factory.PautaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

class PautaControllerTest extends BaseTest{

    @Autowired
    PautaController pautaController;

    @Test
    @DisplayName("Deve criar uma pauta e encotra-la com sucesso")
    void deveCriarUmaPautaComSucesso() {
        PautaRequest pautaRequest = PautaFactory.criarRequest();
        ResponseEntity<Long> criarPautaReponse = pautaController.criar(pautaRequest);
        ResponseEntity<PautaResponse> findByIdPautaResponse = pautaController.findById(criarPautaReponse.getBody());
        Assertions.assertInstanceOf(PautaResponse.class, findByIdPautaResponse.getBody());
        Assertions.assertInstanceOf(Long.class, criarPautaReponse.getBody());
        Assertions.assertEquals(findByIdPautaResponse.getBody().getDescricao(), pautaRequest.getDescricao());
        Assertions.assertEquals(findByIdPautaResponse.getBody().getName(), pautaRequest.getNome());
        Assertions.assertInstanceOf(Long.class, findByIdPautaResponse.getBody().getId());
    }
}