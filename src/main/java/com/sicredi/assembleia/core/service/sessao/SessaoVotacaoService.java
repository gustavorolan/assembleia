package com.sicredi.assembleia.core.service.sessao;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SessaoVotacaoService {
    SessaoVotacaoResponse abrir(AberturaSessaoVotacaoRequest aberturaSessaoVotacaoRequest);

    SessaoVotacaoEntity findById(Long id);

    SessaoVotacaoResponse findResponseById(Long id);

    List<SessaoVotacaoEntity> findAllStatusAberto();

    SessaoVotacaoEntity save(SessaoVotacaoEntity entity);

    Page<SessaoVotacaoResponse> findAllByStatus(int page, SessaoVotacaoEnum status);
}
