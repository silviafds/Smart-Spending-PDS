package Despesa;

import com.smartSpd.smartSpding.Apresentacao.Controller.DespesaController;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl.DespesaServiceImpl;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class DespesaUnitTest {

    private DespesaController despesaController;

    @BeforeEach
    public void init() {
        DespesaServiceImpl despesaService = mock(DespesaServiceImpl.class);
        despesaController = new DespesaController(despesaService);
    }

    @Test
    @DisplayName("Cadastro de Despesa Test")
    public void testCadastrarDespesa() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        DespesaDTO despesaDTO = new DespesaDTO(null, contaInterna, "Despesas Fixas", "Internet",
                LocalDate.now(), 80.0, "Pix", "Banco do Brasil",
                "Corrente/0044/12345678", "Claro", "Banco Bradesco",
                "", "", "internet", 1L, "5");

        despesaController.register(despesaDTO);
    }

    @Test
    @DisplayName("Buscar Despesa Test")
    public void testBuscarDespesa() {
        ResponseEntity<?> response = despesaController.buscarDespesa();
        assertNotNull(response);
    }

    @Test
    @DisplayName("Deletar Despesa Test")
    public void testDeletarDespesa() {
        ResponseEntity<?> response = despesaController.deletarDespesa(4L);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Buscar Categoria de Despesa Test")
    public void testBuscarCategoriaDespesa() {
        ResponseEntity<?> response = despesaController.buscarCategoriaDespesa();
        assertNotNull(response);
    }

   /* @Test
    @DisplayName("Buscar Titúlos Contábeis de Despesa Test")
    public void testBuscarTituloContabilDespesa() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);
        DespesaDTO despesaDTO = new DespesaDTO(null, contaInterna, "Despesas Fixas", "Internet",
                LocalDate.now(), 80.0, "Pix", "Banco Bradesco",
                "Corrente/1258/0001258", "Claro", "Banco Bradesco",
                "", "", "internet", 1L, "5");

        ResponseEntity<?> response = despesaController.editarDespesa(despesaDTO);
        assertNotNull(response);
    }*/

    @Test
    @DisplayName("Dados de Despesa Test")
    public void testDadosBancariosOrigemNotNull() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);

        DespesaDTO despesaDTO = new DespesaDTO(null, contaInterna, "Despesas Fixas", "Internet",
                LocalDate.now(), 80.0, "Pix", "Banco do Brasil",
                "Conta Corrente/0044/12345678", "Claro", "Banco Bradesco",
                "", "", "internet", 1L, "5");

        assertNotNull(despesaDTO.getDadosBancariosOrigem());
    }

    @Test
    @DisplayName("Dados nulos de Despesa Test")
    public void testDadosBancariosOrigemIsNull() {
        ContaInterna contaInterna = new ContaInterna(39L, "conta 2", false);

        DespesaDTO despesaDTO = new DespesaDTO(null, contaInterna, "Despesas Fixas", "Internet",
                LocalDate.now(), 80.0, "Pix", "Banco do Brasil",
                null, "Claro", "Banco Bradesco",
                "", "", "internet", 1L, "5");

        assertNull(despesaDTO.getDadosBancariosOrigem());
    }

}