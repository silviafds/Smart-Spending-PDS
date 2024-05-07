package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.Balanco;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.Balanco.DESPESA;
import static com.smartSpd.smartSpding.Core.Enum.Balanco.RECEITA;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/balancoDespesa")
public class BalancoController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DespesaBalancoService despesaBalancoService;

    private final DespesaReceitaBalancoService despesaReceitaBalancoService;

    private final ReceitaBalancoService receitaBalancoService;

    private final BalancoService balancoService;

    public BalancoController(DespesaBalancoService despesaBalancoService,
                             DespesaReceitaBalancoService despesaReceitaBalancoService,
                             ReceitaBalancoService receitaBalancoService, BalancoService balancoService) {
        this.despesaBalancoService = despesaBalancoService;
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
        this.receitaBalancoService = receitaBalancoService;
        this.balancoService = balancoService;
    }

    @PostMapping("/registroBalancoRapido")
    @Transactional
    public ResponseEntity<?> registroBalancoRapido(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            if(balancoRapidoDTO.getTipoBalanco().equals(DESPESA.getBalanco())) {
                if(balancoRapidoDTO.getAnaliseBalanco().equals(BUSCAR_TODAS_DESPESAS.getTiposBalanco())) {
                    List<BalancoDespesaReceita> balanco = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(balanco);
                }

                List<BalancoDespesa> balanco = despesaBalancoService.balancoMeiosPagamento(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }

            if(balancoRapidoDTO.getTipoBalanco().equals(DESPESA_RECEITA.getTiposBalanco())) {
                List<BalancoDespesaReceita> balanco = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }

            if(balancoRapidoDTO.getTipoBalanco().equals(RECEITA.getBalanco())) {
                if(balancoRapidoDTO.getAnaliseBalanco().equals(BUSCAR_TODAS_RECEITAS.getTiposBalanco())) {
                    List<BalancoDespesaReceita> balanco = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(balanco);
                }

                List<BalancoReceita> balanco = receitaBalancoService.balancoMeiosPagamento(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Houve um erro no processamento do balanço.");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço de quantidade de meios de pagamento. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço de quantidade de meios de pagamento.");
        }
    }

    @PostMapping("/balanco")
    @Transactional
    public ResponseEntity<?> salvarBalanco(@RequestBody @Valid Balanco balanco) {
        try {
            if (balanco == null || balanco.getNome() == null || balanco.getNome().isEmpty() ||
                    balanco.getTipoBalanco() == null || balanco.getTipoBalanco().isEmpty() ||
                    balanco.getAnalise_balanco() == null || balanco.getAnalise_balanco().isEmpty() ||
                    balanco.getDataInicio() == null || balanco.getDataTermino() == null ||
                    balanco.getTipoVisualizacao() == null || balanco.getTipoVisualizacao().isEmpty() ||
                    balanco.getCategoriaTituloContabil() == null || balanco.getCategoriaTituloContabil().isEmpty())  {
                return ResponseEntity.badRequest().body("Todos os campos devem estar preenchidos.");
            } else {
                balancoService.salvarBalanco(balanco);
                return ResponseEntity.ok().body("Balanço ok.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar salvar o balanço.");
        }
    }

    @GetMapping("balancoPorId/{id}")
    @Transactional
    public ResponseEntity<?> buscarBalancoPorId(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity
                        .badRequest()
                        .body("Id inválido!");
            }

            Balanco balanco = balancoService.buscarBalancoPorId(id);

            if (balanco != null) {
                return ResponseEntity.ok(balanco);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço por Id! ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço por Id!");
        }
    }

    @GetMapping("/buscarTodosBalancos")
    @Transactional
    public ResponseEntity<?> buscarTodosBalancos() {
        try {
            List<Balanco> balancos = balancoService.buscarTodosBalancos();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balancos);

        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços! ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar todos os balanços!");
        }
    }
}