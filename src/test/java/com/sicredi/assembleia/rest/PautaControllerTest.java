package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.factory.PautaFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PautaControllerTest extends BaseTest{

    @Autowired
    PautaController pautaController;

    @Test
    void criar() {
        PautaRequest pautaRequest = PautaFactory.criarRequest();
        Long id = pautaController.criar(pautaRequest).getBody();
        pautaController.findById(id);
    }

    @Test
    void findById() {
    }
}