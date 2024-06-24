package com.sicredi.assembleia.core.service.sessao.impl;

import com.sicredi.assembleia.core.entity.SessaoVotacaoCacheEntity;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEntity;
import com.sicredi.assembleia.core.exception.SessaoCacheNotFoundException;
import com.sicredi.assembleia.core.mapper.SessaoVotacaoMapper;
import com.sicredi.assembleia.core.repository.SessaoVotacaoCacheRepository;
import com.sicredi.assembleia.core.service.sessao.SessaoVotacaoCacheService;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoCacheServiceImplTest {

    private final SessaoVotacaoCacheRepository sessaoVotacaoCacheRepository = Mockito.mock(SessaoVotacaoCacheRepository.class);

    private final SessaoVotacaoMapper sessaoVotacaoMapper = new SessaoVotacaoMapper();

    private final SessaoVotacaoCacheService sessaoVotacaoCacheService =
            new SessaoVotacaoCacheServiceImpl(sessaoVotacaoCacheRepository, sessaoVotacaoMapper);

    @Captor
    private ArgumentCaptor<SessaoVotacaoCacheEntity> sessaoVotacaoCacheCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sessaoVotacaoCacheService, "sessaoCacheTtl", "86400");
    }

    @Test
    @DisplayName("Deve inserir sessao no cache corretamente.")
    void inserirSessaoVotacaoEmCache() {

        SessaoVotacaoEntity sessaoVotacaoEntity = SessaoVotacaoFactory.criarEntidade();
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.entidadeCacheBuilder()
                .build();

        Mockito.when(sessaoVotacaoCacheRepository.save(Mockito.any(SessaoVotacaoCacheEntity.class)))
                .thenReturn(sessaoVotacaoCacheEntity);

        sessaoVotacaoCacheService.inserirSessaoVotacaoEmCache(sessaoVotacaoEntity);

        Mockito.verify(sessaoVotacaoCacheRepository, Mockito.times(1))
                .save(sessaoVotacaoCacheCaptor.capture());

        Assertions.assertEquals(sessaoVotacaoCacheEntity, sessaoVotacaoCacheCaptor.getValue());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoCacheRepository);
    }

    @Test
    @DisplayName("Deve retornar uma sessão votação do cache corretamente.")
    void deveRetornarUmaSessaoDeVotacaoEmCacheCorretamente() {
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.entidadeCacheBuilder()
                .build();

        Mockito.when(sessaoVotacaoCacheRepository.findById(sessaoVotacaoCacheEntity.getId()))
                .thenReturn(Optional.of(sessaoVotacaoCacheEntity));

        SessaoVotacaoCacheEntity response = sessaoVotacaoCacheService.findById(sessaoVotacaoCacheEntity.getId());

        Mockito.verify(sessaoVotacaoCacheRepository, Mockito.times(1))
                .findById(sessaoVotacaoCacheEntity.getId());

        Assertions.assertEquals(sessaoVotacaoCacheEntity, response);

        Mockito.verifyNoMoreInteractions(sessaoVotacaoCacheRepository);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar encotrar uma sessão votação do cache.")
    void deveLancarUmaExcecaoAoTentarEncotrarSessaoDeVotacaoEmCache() {
        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.entidadeCacheBuilder()
                .build();

        Mockito.when(sessaoVotacaoCacheRepository.findById(sessaoVotacaoCacheEntity.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(SessaoCacheNotFoundException.class, () ->
                sessaoVotacaoCacheService.findById(sessaoVotacaoCacheEntity.getId())
        );

        Mockito.verify(sessaoVotacaoCacheRepository, Mockito.times(1))
                .findById(sessaoVotacaoCacheEntity.getId());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoCacheRepository);
    }


    @Test
    @DisplayName("Deve deletar corretamente uma entidade de cache.")
    void deveDeletarEntidadeNaSessaoVotacaoEmCache() {

        SessaoVotacaoCacheEntity sessaoVotacaoCacheEntity = SessaoVotacaoFactory.criarEntidadeCache();

        sessaoVotacaoCacheService.delete(sessaoVotacaoCacheEntity.getId());

        Mockito.verify(sessaoVotacaoCacheRepository, Mockito.times(1)).deleteById(idCaptor.capture());

        Assertions.assertEquals(sessaoVotacaoCacheEntity.getId(), idCaptor.getValue());

        Mockito.verifyNoMoreInteractions(sessaoVotacaoCacheRepository);
    }
}