package com.sicredi.assembleia.core.service.associado.impl;

import com.sicredi.assembleia.core.entity.AssociadoEntity;
import com.sicredi.assembleia.core.repository.AssociadoRepository;
import com.sicredi.assembleia.factory.AssociadoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceImplTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @InjectMocks
    private AssociadoServiceImpl associadoService;

    @Test
    @DisplayName("Deve retornar um associado que já está salvo no banco")
    void deveRetornarUmAssociadoJaExistenteNoBanco() {
        AssociadoEntity associadoEntity = AssociadoFactory.criarEntity();

        Mockito.when(associadoRepository.findByCpf(associadoEntity.getCpf())).thenReturn(Optional.of(associadoEntity));

        AssociadoEntity response = associadoService.findOrInsert(associadoEntity.getCpf());

        Mockito.verify(associadoRepository, Mockito.times(1)).findByCpf(associadoEntity.getCpf());

        Mockito.verifyNoMoreInteractions(associadoRepository);

        Assertions.assertEquals(associadoEntity, response);
    }

    @Test
    @DisplayName("Deve retornar um associado e salva-lo no banco")
    void deveRetornarUmAssociadoESalvarNoBanco() {
        AssociadoEntity associadoEntity = AssociadoFactory.criarEntity();

        Mockito.when(associadoRepository.findByCpf(associadoEntity.getCpf())).thenReturn(Optional.empty());

        Mockito.when(associadoRepository.save(associadoEntity)).thenReturn(associadoEntity);

        AssociadoEntity response = associadoService.findOrInsert(associadoEntity.getCpf());

        Mockito.verify(associadoRepository, Mockito.times(1)).findByCpf(associadoEntity.getCpf());

        Mockito.verify(associadoRepository, Mockito.times(1)).save(associadoEntity);

        Mockito.verifyNoMoreInteractions(associadoRepository);

        Assertions.assertEquals(associadoEntity, response);
    }
}