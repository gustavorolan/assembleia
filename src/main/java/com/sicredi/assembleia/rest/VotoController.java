package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.service.voto.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/voto")
@RequiredArgsConstructor
@Tag(name = "VotoController", description = "Endpoint de Voto")
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    @Operation(
            summary = "Votar",
            description = "Endpoint para votar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "NotFound"),
    })
    public ResponseEntity<Object> votar(@Valid @RequestBody VotoRequest votoRequest) {
        votoService.votar(votoRequest);
        return ResponseEntity.noContent().build();
    }
}


