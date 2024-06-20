package com.sicredi.assembleia.core.service.pauta;

import com.sicredi.assembleia.core.dto.PautaDto;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;

public interface PautaService {
    Long criar(PautaDto pautaDto);

    PautaEntity findById(Long id);

    PautaResponse findPautaResponseById(Long id);
}
