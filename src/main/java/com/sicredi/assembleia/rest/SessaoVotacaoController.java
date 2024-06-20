package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/sessao")
@RequiredArgsConstructor

public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    @PostMapping
    public ResponseEntity<SessaoVotacaoResponse> abrir(@Valid @RequestBody AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest) {
        SessaoVotacaoResponse response = sessaoVotacaoService.abrir(aberturaSessaoVotacaoRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoVotacaoResponse> findById(@PathVariable Long id) {
        SessaoVotacaoResponse response = sessaoVotacaoService.findResponseById(id);
        return ResponseEntity.ok(response);
    }
}


