package com.sicredi.assembleia.factory.service;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;

public class PautaFactory {
    public static PautaEntity criarEntity() {

        return entityBuilder().build();
    }

    public static PautaEntity.PautaEntityBuilder entityBuilder() {

        return PautaEntity.builder()
                .name("name")
                .id(1L)
                .descricao("desc");
    }

    public static PautaRequest criarRequest() {
        return requestBuilder().build();
    }

    public static PautaRequest.PautaRequestBuilder requestBuilder() {
        return PautaRequest.builder()
                .nome("name")
                .descricao("desc");
    }

    public static PautaResponse criarResponse() {
        return responseBuilder().build();
    }

    public static PautaResponse.PautaResponseBuilder responseBuilder() {
        return PautaResponse.builder()
                .id(1L)
                .name("name")
                .descricao("desc");
    }
}
