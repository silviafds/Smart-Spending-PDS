package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/balancoDespesa")
public class BalancoDespesaController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));


    private final DespesaBalancoService despesaBalancoService;

    public BalancoDespesaController(DespesaBalancoService despesaBalancoService) {
        this.despesaBalancoService = despesaBalancoService;
    }

    @GetMapping("/buscarMeiosPagamento")
    @Transactional
    public ResponseEntity<?> buscarMeiosPagamento() {
        try {
            List<BalancoDespesa> balanco = despesaBalancoService.listagemMeiosPagamento();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balanco);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço de quantidade de meios de pagamento. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar balanço de quantidade de meios de pagamento.");
        }
    }
}
