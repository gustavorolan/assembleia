package com.sicredi.assembleia.core.service.pauta.impl;

import com.sicredi.assembleia.core.dto.PautaDto;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.exception.PautaNotFoundException;
import com.sicredi.assembleia.core.repository.PautaRepository;
import com.sicredi.assembleia.core.repository.SessaoVotacaoCacheRepository;
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

    private final SessaoVotacaoCacheRepository sessaoVotacaoCacheRepository;

    @Override
    public Long criar(PautaDto pautaDto) {
        PautaEntity pautaEntity = pautaMapper.pauteDtoToEntity(pautaDto);
        PautaEntity pautaEntitySalva = pautaRepository.save(pautaEntity);
        logger.info("Criado um pauta com sucesso!");
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
