package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.PautaDto;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.service.pauta.impl.PautaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v0/pauta")
@RequiredArgsConstructor
public class PautaController {

    private final PautaServiceImpl pautaServiceImpl;

    @PostMapping
    public ResponseEntity<Long> criar(@Valid @RequestBody PautaDto pautaDto) {
        Long response = pautaServiceImpl.criar(pautaDto);
        return ResponseEntity.created(URI.create("/api/v0/pauta/" + response.toString()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponse> findById(@PathVariable Long id) {
        PautaResponse response = pautaServiceImpl.findPautaResponseById(id);
        return ResponseEntity.ok(response);

    }
}


