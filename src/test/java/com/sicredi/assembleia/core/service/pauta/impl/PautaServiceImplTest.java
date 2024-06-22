package com.sicredi.assembleia.core.service.pauta.impl;

import com.sicredi.assembleia.core.dto.PautaRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.entity.PautaEntity;
import com.sicredi.assembleia.core.exception.PautaNotFoundException;
import com.sicredi.assembleia.core.mapper.PautaMapper;
import com.sicredi.assembleia.core.repository.PautaRepository;
import com.sicredi.assembleia.factory.PautaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PautaServiceImplTest {

    private final PautaRepository pautaRepository = Mockito.mock(PautaRepository.class);

    private final PautaMapper pautaMapper = new PautaMapper();

    @InjectMocks
    private PautaServiceImpl pautaService = new PautaServiceImpl(pautaRepository, pautaMapper);

    @Captor
    private ArgumentCaptor<PautaEntity> pautaCaptor;

    @Test
    @DisplayName("Deve criar uma entidade corretamente")
    void deveCriarUmaEntidadeCorretamente() {
        PautaRequest pautaRequest = PautaFactory.criarRequest();
        PautaEntity pautaEntity = PautaFactory.criarEntity();
        PautaEntity pautaEntityExpected = PautaFactory.entityBuilder()
                .id(null)
                .build();

        Mockito.when(pautaRepository.save(Mockito.any(PautaEntity.class)))
                .thenReturn(pautaEntity);

        Long response = pautaService.criar(pautaRequest);

        Mockito.verify(pautaRepository).save(pautaCaptor.capture());

        Mockito.verifyNoMoreInteractions(pautaRepository);

        Assertions.assertEquals(pautaEntity.getId(), response);
        Assertions.assertEquals(pautaEntityExpected, pautaCaptor.getValue());
    }

    @Test
    @DisplayName("Deve econtrar entidade pelo id")
    void deveEncotrarUmaEntidadePeloId() {
        PautaEntity pautaEntity = PautaFactory.criarEntity();

        Mockito.when(pautaRepository.findById(pautaEntity.getId()))
                .thenReturn(Optional.of(pautaEntity));

        PautaEntity response = pautaService.findById(pautaEntity.getId());

        Mockito.verify(pautaRepository).findById(pautaEntity.getId());
        Mockito.verifyNoMoreInteractions(pautaRepository);

        Assertions.assertEquals(pautaEntity, response);
    }

    @Test
    @DisplayName("Deve lançar um exception ao tentar encontrar entidade pelo id")
    void deveLancarExcecaoTentandoEncontrarEntidadePeloId() {
        PautaEntity pautaEntity = PautaFactory.criarEntity();

        Mockito.when(pautaRepository.findById(pautaEntity.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(PautaNotFoundException.class, () ->
                pautaService.findById(pautaEntity.getId())
        );

        Mockito.verify(pautaRepository).findById(pautaEntity.getId());
        Mockito.verifyNoMoreInteractions(pautaRepository);
    }

    @Test
    @DisplayName("Deve econtrar entidade pelo id e mappear para o dto de response")
    void deveEncotrarUmaEntidadePeloIdERetornarResponse() {
        PautaEntity pautaEntity = PautaFactory.criarEntity();
        PautaResponse pautaResponse = PautaFactory.criarResponse();

        Mockito.when(pautaRepository.findById(pautaEntity.getId()))
                .thenReturn(Optional.of(pautaEntity));

        PautaResponse response = pautaService.findPautaResponseById(pautaEntity.getId());

        Mockito.verify(pautaRepository).findById(pautaEntity.getId());
        Mockito.verifyNoMoreInteractions(pautaRepository);

        Assertions.assertEquals(pautaResponse, response);
    }
}