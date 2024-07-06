package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl.HospitalBalancosStrategyImpl;
import com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl.RestauranteBalancosStrategyImpl;
import com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl.SupermercadoBalancosStrategyImpl;
import com.smartSpd.smartSpding.Core.CasoUso.*;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    private final Map<String, Function<BalancoRapidoDTO, List<?>>> balancoHandlers = new HashMap<>();

    private final HospitalBalancosStrategyImpl hospitalBalancosStrategy;
    private  final RestauranteBalancosStrategyImpl restauranteBalancosStrategy;
    private final SupermercadoBalancosStrategyImpl supermercadoBalancosStrategy;


    public BalancoController(DespesaBalancoService despesaBalancoService,
                             DespesaReceitaBalancoService despesaReceitaBalancoService,
                             ReceitaBalancoService receitaBalancoService, BalancosService balancosService,
                             HospitalBalancosStrategyImpl hospitalBalancosStrategy,
                             RestauranteBalancosStrategyImpl restauranteBalancosStrategy,
                             SupermercadoBalancosStrategyImpl supermercadoBalancosStrategy) {
        this.despesaBalancoService = despesaBalancoService;
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
        this.receitaBalancoService = receitaBalancoService;
        this.balancosService = balancosService;
        this.hospitalBalancosStrategy = hospitalBalancosStrategy;
        this.restauranteBalancosStrategy = restauranteBalancosStrategy;
        this.supermercadoBalancosStrategy = supermercadoBalancosStrategy;

        balancoHandlers.put(DESPESA.getBalanco(), this::balancosDespesas);
        balancoHandlers.put(DESPESA_RECEITA.getTiposBalanco(), this::balancosDespesasReceitas);
        balancoHandlers.put(RECEITA.getBalanco(), this::balancosReceitas);
    }

    @PostMapping("/registroBalancoRapido")
    public ResponseEntity<?> registroBalancoRapido(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            List<?> balanco = balancosService.gerenciadorBalancos(balancoRapidoDTO);
            //Function<BalancoRapidoDTO, List<?>> handler = balancoHandlers.get(balancoRapidoDTO.getTipoBalanco());

            if (balanco != null) {
                //List<?> balanco = handler.apply(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("teste");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço de quantidade de meios de pagamento. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço de quantidade de meios de pagamento.");
        }
    }

    private List<?> balancosDespesas(BalancoRapidoDTO balancoRapidoDTO) {
        if(balancoRapidoDTO.getAnaliseBalanco().equals(BUSCAR_TODAS_DESPESAS.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }

        return despesaBalancoService.balancoMeiosPagamento(balancoRapidoDTO);
    }

    private List<?> balancosReceitas(BalancoRapidoDTO balancoRapidoDTO) {
        if(balancoRapidoDTO.getAnaliseBalanco().equals(BUSCAR_TODAS_RECEITAS.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }

        return receitaBalancoService.balancoMeiosPagamento(balancoRapidoDTO);
    }

    private List<?> balancosDespesasReceitas(BalancoRapidoDTO balancoRapidoDTO) {
        return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
    }

    @PostMapping("/registrarBalanco")
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
    public ResponseEntity<?> buscarTodosBalancos() {
        try {
            List<Balancos> balancos = balancosService.buscarTodosBalancos();
            System.out.println("balancos: "+balancos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balancos);
        } catch(Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanços.");
        }
    }

    @GetMapping("/buscarBalancosHospital")
    public ResponseEntity<?> buscarBalancosHospital() {
        try {
            List<Balancos> balancos = balancosService.buscarBalancosHospital();
            System.out.println("balancos: "+balancos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balancos);
        } catch(Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanços.");
        }
    }

    @GetMapping("/buscarBalancosSupermercado")
    public ResponseEntity<?> buscarBalancosSupermercado() {
        try {
            List<Balancos> balancos = balancosService.buscarBalancosSupermercado();
            System.out.println("balancos: "+balancos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balancos);
        } catch(Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanços.");
        }
    }

    @GetMapping("/buscarBalancosRestaurante")
    public ResponseEntity<?> buscarBalancosRestaurante() {
        try {
            List<Balancos> balancos = balancosService.buscarBalancosRestaurante();
            System.out.println("balancos: "+balancos);
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
    public ResponseEntity<?> buscarBalancoPorId(@PathVariable Long id) {
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
    public ResponseEntity<?> deletarBalanco(@PathVariable Long id) {
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

    @PatchMapping("/editarBalancoDashboard")
    public ResponseEntity<?> editarBalancoDashboard(@RequestBody @Valid DashDTO dados) {
        try {
            balancosService.editarBalancoNoDashboard(dados);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message:\": \"Balanço editado com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar balanço. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar balanço.");
        }
    }
}