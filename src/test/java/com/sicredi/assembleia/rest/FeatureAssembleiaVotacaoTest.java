package com.sicredi.assembleia.rest;

import com.sicredi.assembleia.core.dto.AberturaSessaoVotacaoRequest;
import com.sicredi.assembleia.core.dto.PautaResponse;
import com.sicredi.assembleia.core.dto.SessaoVotacaoResponse;
import com.sicredi.assembleia.core.entity.SessaoVotacaoEnum;
import com.sicredi.assembleia.core.exception.PautaNotFoundException;
import com.sicredi.assembleia.core.exception.SessaoNotFoundException;
import com.sicredi.assembleia.factory.rest.PautaControllerFactory;
import com.sicredi.assembleia.factory.rest.SessaoVotacaoControllerFactory;
import com.sicredi.assembleia.factory.rest.VotoControllerFactory;
import com.sicredi.assembleia.factory.service.SessaoVotacaoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class FeatureAssembleiaVotacaoTest extends BaseTest {

    @Test
    @DisplayName("Deve fazer todo fluxo de inserção corretamente e esperar resultado.")
    public void deveFazerTodoFluxoDeInsercaoCorretamente() {
        List<String> cpfsFavor = List.of("00011122230", "00011122231", "00011122232", "00011122233", "00011122234", "00011122235", "00011122236");
        List<String> cpfsContra = List.of("00011122210", "00011122211", "00011122212", "00011122213", "00011122214");

        PautaResponse pautaResponse = PautaControllerFactory.deveCriarUmaPautaComSucesso(pautaController);
        SessaoVotacaoResponse sessaoVotacaoResponse = SessaoVotacaoControllerFactory.deveAbrirUmaSessaoComSucesso(sessaoVotacaoController, pautaResponse.getId());

        cpfsFavor.forEach(cpf -> VotoControllerFactory.deveConseguirEfetuarVotoFavorSessaoAberta(votoController, sessaoVotacaoResponse.getId(), cpf));

        cpfsContra.forEach(cpf -> VotoControllerFactory.deveConseguirEfetuarVotoContraSessaoAberta(votoController, sessaoVotacaoResponse.getId(), cpf));

        await(65000);

        SessaoVotacaoResponse sessaoVotacaoResponseFindById = Objects.requireNonNull(sessaoVotacaoController.findById(sessaoVotacaoResponse.getId()).getBody());

        Assertions.assertEquals(sessaoVotacaoResponseFindById.getTotal(), cpfsFavor.size()+cpfsContra.size());
        Assertions.assertEquals(sessaoVotacaoResponseFindById.getVotosFavor(), cpfsFavor.size());
        Assertions.assertEquals(sessaoVotacaoResponseFindById.getVotosContra(), cpfsContra.size());
        Assertions.assertEquals(sessaoVotacaoResponseFindById.getStatus(), SessaoVotacaoEnum.ENCERRADA);
    }

    @Test
    @DisplayName("Deve Falhar ao consultar pauta inexistente.")
    public void deveFalharAoProcurarPautaInexistente() {

        Assertions.assertThrowsExactly(PautaNotFoundException.class, () -> {
            pautaController.findById(5000L);
        });
    }

    @Test
    @DisplayName("Deve Falhar ao consultar pauta inexistente.")
    public void deveFalharAoProcurarSessaoInexistente() {

        Assertions.assertThrowsExactly(SessaoNotFoundException.class, () -> {
            sessaoVotacaoController.findById(5000L);
        });
    }

    @Test
    @DisplayName("Deve Falhar ao consultar pauta inexistente.")
    public void deveFalharAoAbrirSessaoPautaInexistente() {

        AberturaSessaoVotacaoRequest request = SessaoVotacaoFactory.requestAberturaBuilder().pautaId(5000L).build();

        Assertions.assertThrowsExactly(PautaNotFoundException.class, () -> {
            sessaoVotacaoController.abrir(request);
        });
    }
}
