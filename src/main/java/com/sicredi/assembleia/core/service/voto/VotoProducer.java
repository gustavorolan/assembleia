package com.sicredi.assembleia.core.service.voto;

import com.sicredi.assembleia.core.dto.VotoRequest;

public interface VotoProducer {
    void send(VotoRequest votoRequest);
}
