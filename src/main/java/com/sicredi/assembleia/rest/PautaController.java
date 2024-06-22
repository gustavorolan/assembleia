package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.service.pauta.impl.PautaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v0/pauta")
@RequiredArgsConstructor
@Tag(name = "PautaController", description = "Endpoint de Pauta")
public class PautaController {

    private final PautaServiceImpl pautaServiceImpl;

    @PostMapping
    @Operation(
            summary = "Criar Pauta",
            description = "Endpoint criar uma Pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<Long> criar(@Valid @RequestBody PautaRequest pautaRequest) {
        Long response = pautaServiceImpl.criar(pautaRequest);
        return ResponseEntity.created(URI.create("/api/v0/pauta/" + response.toString()))
                .body(response);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Pauta pelo Id",
            description = "Endpoint para encontrar a pauta pelo Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<PautaResponse> findById(@PathVariable Long id) {
        PautaResponse response = pautaServiceImpl.findPautaResponseById(id);
        return ResponseEntity.ok(response);

    }
}


