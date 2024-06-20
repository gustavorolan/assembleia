package com.sicredi.assembleia.core.service.voto;

import com.sicredi.assembleia.core.dto.VotoRequest;

public interface VotoService {
    void votar(VotoRequest votoRequest);

    void consume(VotoRequest votoRequest);
}
