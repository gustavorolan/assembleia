package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.VotoRequest;
import com.sicredi.assembleia.core.service.voto.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/voto")
@RequiredArgsConstructor

public class VotoController {

    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<Object> votar(@Valid @RequestBody VotoRequest votoRequest) {
        votoService.votar(votoRequest);
        return ResponseEntity.noContent().build();
    }
}


