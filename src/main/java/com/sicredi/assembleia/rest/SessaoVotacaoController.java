package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/sessao")
@RequiredArgsConstructor
@Tag(name = "SessaoVotacaoController", description = "Endpoint de Sessao Votacao")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    @PostMapping
    @Operation(
            summary = "Abrir Sessão Votação",
            description = "Endpoint para realizar a abertura de uma sessão de votação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<SessaoVotacaoResponse> abrir(@Valid @RequestBody AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest) {
        SessaoVotacaoResponse response = sessaoVotacaoService.abrir(aberturaSessaoVotacaoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Sessão votação pelo Id",
            description = "Endpoint para encontrar uma sessão de votação pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<SessaoVotacaoResponse> findById(@PathVariable Long id) {
        SessaoVotacaoResponse response = sessaoVotacaoService.findResponseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Sessão votação pelo Id",
            description = "Endpoint para encontrar uma sessão de votação pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<Page<SessaoVotacaoResponse>> findByAllByStatus(
            @Schema(description = "Página desejada", example = "0")
            @RequestParam("page") int page,
            @Schema(description = "Sessão status pode ser ABERTA ou ENCERRADA", example = "ABERTA")
            @RequestParam("status") SessaoVotacaoEnum status
    ) {
        Page<SessaoVotacaoResponse> response = sessaoVotacaoService.findAllByStatus(page, status);
        return ResponseEntity.ok(response);
    }
}


