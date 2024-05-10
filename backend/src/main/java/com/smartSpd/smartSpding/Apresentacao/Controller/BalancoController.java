package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.BalancosService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
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

import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.DESPESA;
import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.RECEITA;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/balancoDespesa")
public class BalancoController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DespesaBalancoService despesaBalancoService;

    private final DespesaReceitaBalancoService despesaReceitaBalancoService;

    private final ReceitaBalancoService receitaBalancoService;

    private final BalancosService balancosService;

    public BalancoController(DespesaBalancoService despesaBalancoService,
                             DespesaReceitaBalancoService despesaReceitaBalancoService,
                             ReceitaBalancoService receitaBalancoService, BalancosService balancosService) {
        this.despesaBalancoService = despesaBalancoService;
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
        this.receitaBalancoService = receitaBalancoService;
        this.balancosService = balancosService;
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

    @PostMapping("/registrarBalanco")
    @Transactional
    public ResponseEntity<?> cadastrarBalanco(@RequestBody @Valid Balancos balancos) {
        try {
            balancosService.registrarBalanco(balancos);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanco cadastrado com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar balanço. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar balanço no sistema.");
        }
    }

    @PatchMapping("/editarBalanco")
    @Transactional
    public ResponseEntity<?> editarBalanco(@RequestBody @Valid Balancos dados) {
        try {
            balancosService.editarBalanco(dados);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message:\": \"Balanço editado com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar balanço. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar balanço.");
        }
    }

    @GetMapping("/buscarBalancos")
    @Transactional
    public ResponseEntity<?> buscarTodosBalancos() {
        try {
            List<Balancos> balancos = balancosService.buscarTodosBalancos();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balancos);
        } catch(Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanços.");
        }
    }

    @GetMapping("/buscarBalancoPorId/{id}")
    @Transactional
    public ResponseEntity<?> buscarBalancoPorId(@PathVariable int id) {
        try {
            Balancos balanco = balancosService.buscarBalancoPorId(id);
            if (balanco != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Balanco não encontrado para o id: " + id);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço por id. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço por id: " + id);
        }
    }

    @DeleteMapping("/deletarBalanco/{id}")
    @Transactional
    public ResponseEntity<?> deletarBalanco(@PathVariable int id) {
        try {
            balancosService.deletarBalanco(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço deletado com sucesso.\"}");
        } catch (BalancoNaoEncontradoException e) {
            log.warning("Balanço com o id " + id + "não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço não encontrado.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar balanço.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar balanço.");
        }
    }
}