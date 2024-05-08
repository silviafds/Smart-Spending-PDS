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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
                    .body("deu certo");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço de quantidade de meios de pagamento. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço de quantidade de meios de pagamento.");
        }
    }
}