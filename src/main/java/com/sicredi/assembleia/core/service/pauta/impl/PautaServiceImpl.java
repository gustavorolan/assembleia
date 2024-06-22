package com.sicredi.assembleia.core.service.pauta.impl;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.exception.PautaNotFoundException;
import com.sicredi.assembleia.core.repository.PautaRepository;
import com.sicredi.assembleia.core.service.pauta.PautaService;
import com.sicredi.assembleia.core.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PautaServiceImpl implements PautaService {

    private static final Logger logger = LoggerFactory.getLogger(PautaServiceImpl.class);

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    @Override
    public Long criar(PautaRequest pautaRequest) {
        PautaEntity pautaEntity = pautaMapper.pauteDtoToEntity(pautaRequest);
        PautaEntity pautaEntitySalva = pautaRepository.save(pautaEntity);
        logger.info("Foi criada uma pauta com sucesso. pautaId{}", pautaEntity.getId());
        return pautaEntitySalva.getId();
    }


    @Override
    public PautaEntity findById(Long id) {

        PautaNotFoundException pautaNotFoundException = new PautaNotFoundException();

        logger.info("Consultando uma pauta com o id: {}", id);

        return pautaRepository.findById(id).orElseThrow(() -> {
                    logger.error(pautaNotFoundException.getMessage(), pautaNotFoundException);
                    return pautaNotFoundException;
                }
        );
    }

    @Override
    public PautaResponse findPautaResponseById(Long id) {
        PautaEntity pautaEntity = findById(id);
        return pautaMapper.pautaEntityToResponse(pautaEntity);
    }

}
