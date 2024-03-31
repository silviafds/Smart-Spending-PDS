package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/balancoDespesa")
public class BalancoDespesaController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DespesaBalancoService despesaBalancoService;

    private final DespesaReceitaBalancoService despesaReceitaBalancoService;

    public BalancoDespesaController(DespesaBalancoService despesaBalancoService,
                                    DespesaReceitaBalancoService despesaReceitaBalancoService) {
        this.despesaBalancoService = despesaBalancoService;
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @PostMapping("/registroBalancoRapido")
    @Transactional
    public ResponseEntity<?> registroBalancoRapido(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            if(balancoRapidoDTO.getTipoBalanco().equals("Despesa")) {
                List<BalancoDespesa> balanco = despesaBalancoService.balancoMeiosPagamento(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }
            if(balancoRapidoDTO.getTipoBalanco().equals("Despesa e Receita")) {
                List<BalancoDespesaReceita> balanco = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(balanco);
            }

            if(balancoRapidoDTO.getTipoBalanco().equals("Receita")) {
               //implementação aqui
            }
            //coloca o return de receita
            return ResponseEntity.status(HttpStatus.OK)
                    .body("deu bom.");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço de quantidade de meios de pagamento. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço de quantidade de meios de pagamento.");
        }
    }

}