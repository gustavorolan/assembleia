package com.sicredi.assembleia.core.service.pauta;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;

public interface PautaService {
    PautaResponse criar(PautaRequest pautaRequest);

    PautaEntity findById(Long id);

    PautaResponse findPautaResponseById(Long id);
}
