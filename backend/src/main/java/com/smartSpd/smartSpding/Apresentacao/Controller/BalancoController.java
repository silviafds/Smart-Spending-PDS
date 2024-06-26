package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.BalancosService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoCategoriaDTO;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Core.Strategy.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Strategy.HospitalBalancoStrategy;
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

    private final Map<String, BalancoStrategy> balancoStrategyMap = new HashMap<>();

    public BalancoController(DespesaBalancoService despesaBalancoService,
                             DespesaReceitaBalancoService despesaReceitaBalancoService,
                             ReceitaBalancoService receitaBalancoService, BalancosService balancosService, HospitalBalancoStrategy hospitalBalancoStrategy) {
        this.despesaBalancoService = despesaBalancoService;
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
        this.receitaBalancoService = receitaBalancoService;
        this.balancosService = balancosService;
        this.balancoStrategyMap.put("hospital", hospitalBalancoStrategy);

        balancoHandlers.put(DESPESA.getBalanco(), this::balancosDespesas);
        balancoHandlers.put(DESPESA_RECEITA.getTiposBalanco(), this::balancosDespesasReceitas);
        balancoHandlers.put(RECEITA.getBalanco(), this::balancosReceitas);
    }

    private BalancoStrategy getBalancoStrategy(String tipoEstabelecimento) {
        return balancoStrategyMap.getOrDefault(tipoEstabelecimento, null);
    }

    @PostMapping("/registrarBalancoFramework")
    public ResponseEntity<?> cadastrarBalancoFramework(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            BalancoStrategy balancoStrategy = getBalancoStrategy(balancoRapidoDTO.getTipoEstabelecimento());
            if (balancoStrategy == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tipo de estabelecimento desconhecido.");
            }

            Balancos balancos = new Balancos();
            balancos.setTipoEstabelecimento(balancoRapidoDTO.getTipoEstabelecimento());
            balancos.setNome(balancoRapidoDTO.getNome());
            balancos.setTipoBalanco(balancoRapidoDTO.getTipoBalanco());
            balancos.setAnalise_balanco(balancoRapidoDTO.getAnaliseBalanco());
            balancos.setData_inicio(balancoRapidoDTO.getDataInicio());
            balancos.setData_termino(balancoRapidoDTO.getDataTermino());
            balancos.setTipo_visualizacao(balancoRapidoDTO.getTipoVisualizacao());

            for (BalancoCategoriaDTO categoriaDTO : balancoRapidoDTO.getCategorias()) {
                balancoStrategy.criarCategoria(categoriaDTO.getNomeCategoria(), categoriaDTO.getValorGasto(), categoriaDTO.getValorInvestimento(), balancos);
            }

            balancosService.registrarBalanco(balancos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço framework cadastrado com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar balanço framework.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar balanço framework no sistema.");
        }
    }

    @PatchMapping("/editarBalancoFramework")
    public ResponseEntity<?> editarBalancoFramework(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            BalancoStrategy balancoStrategy = getBalancoStrategy(balancoRapidoDTO.getTipoEstabelecimento());
            if (balancoStrategy == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tipo de estabelecimento desconhecido.");
            }

            Balancos balancos = new Balancos();
            balancos.setTipoEstabelecimento(balancoRapidoDTO.getTipoEstabelecimento());
            balancos.setNome(balancoRapidoDTO.getNome());
            balancos.setTipoBalanco(balancoRapidoDTO.getTipoBalanco());
            balancos.setAnalise_balanco(balancoRapidoDTO.getAnaliseBalanco());
            balancos.setData_inicio(balancoRapidoDTO.getDataInicio());
            balancos.setData_termino(balancoRapidoDTO.getDataTermino());
            balancos.setTipo_visualizacao(balancoRapidoDTO.getTipoVisualizacao());

            for (BalancoCategoriaDTO categoriaDTO : balancoRapidoDTO.getCategorias()) {
                balancoStrategy.criarCategoria(categoriaDTO.getNomeCategoria(), categoriaDTO.getValorGasto(), categoriaDTO.getValorInvestimento(), balancos);
            }

            balancosService.editarBalanco(balancos);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço framework editado com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar balanço framework. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar balanço framework.");
        }
    }

    @PostMapping("/registroBalancoRapido")
    public ResponseEntity<?> registroBalancoRapido(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            Function<BalancoRapidoDTO, List<?>> handler = balancoHandlers.get(balancoRapidoDTO.getTipoBalanco());

            if (handler != null) {
                List<?> balanco = handler.apply(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tipo de balanço desconhecido.");
            }
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