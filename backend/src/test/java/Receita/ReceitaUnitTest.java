package Receita;

import com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl.ReceitaServiceImpl;
import com.smartSpd.smartSpding.Apresentacao.Controller.ReceitaController;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ReceitaUnitTest {
    private ReceitaController receitaController;

    @BeforeEach
    public void init() {
        ReceitaServiceImpl receitaService = mock(ReceitaServiceImpl.class);
        receitaController = new ReceitaController(receitaService);
    }

    @Test
    @DisplayName("Cadastro de Receita Test")
    public void testCadastrarReceita() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        ReceitaDTO receitaDTO = new ReceitaDTO(null, contaInterna, "Investimento", "Lucro com moedas",
                LocalDate.now(), 3000.0, "Corretora Ricco", "Pix", "Corretora Ricco",
                "", "", "Banco Bradesco",
                "Corrente/1234/95175328", "Lucro com conversão de moedas","1");

        receitaController.register(receitaDTO);
    }

    @Test
    @DisplayName("Buscar Receita Test")
    public void testBuscarReceita() {
        ResponseEntity<?> response = receitaController.buscarReceitas();
        assertNotNull(response);
    }

    @Test
    @DisplayName("Deletar Receita Test")
    public void testDeletarReceita() {
        ResponseEntity<?> response = receitaController.deletarReceita(4L);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Buscar Categoria de Receita Test")
    public void testBuscarCategoriaReceita() {
        ResponseEntity<?> response = receitaController.buscarCategoriaReceita();
        assertNotNull(response);
    }

    /*@Test
    @DisplayName("Editar Receita Test")
    public void testEditarBuscarTituloContabilReceita() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        ReceitaDTO receitaDTO = new ReceitaDTO(null, contaInterna, "Investimento", "Lucro com moedas",
                LocalDate.now(), 80.0, "Pix", "Banco Bradesco",
                "Corrente/1258/0001258", "Claro", "Banco Bradesco",
                "", "", "internet", "1");

        ResponseEntity<?> response = receitaController.editarReceita(receitaDTO);
        assertNotNull(response);
    }*/

    @Test
    @DisplayName("Dados de Receita Test")
    public void testDadosBancariosOrigemNotNull() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        ReceitaDTO receitaDTO = new ReceitaDTO(null, contaInterna, "Investimento", "Lucro com moedas",
                LocalDate.now(), 80.0, "Pix", "Banco Bradesco",
                "Corrente/1258/0001258", "Claro", "Banco Bradesco",
                "", "", "internet", "1");

        assertNotNull(receitaDTO.getDadosBancariosDestino());
    }

    @Test
    @DisplayName("Dados nulos de Receita Test")
    public void testDadosBancariosOrigemIsNull() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        ReceitaDTO receitaDTO = new ReceitaDTO(null, contaInterna, "Investimento", "Lucro com moedas",
                LocalDate.now(), 80.0, "Pix", "Banco Bradesco",
                "Corrente/1258/0001258", "Claro", "Banco Bradesco",
                "", "", "internet", "1");

        assertNotNull(receitaDTO.getDadosBancariosDestino());
    }
}
