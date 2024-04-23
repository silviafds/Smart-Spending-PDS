package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/saldo")
public class SaldoController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final SaldoService saldoService;

    @Autowired
    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping("/buscarSaldos")
    @Transactional
    public ResponseEntity<?> buscarSaldos() {
        try {
            Map<String, BigDecimal> saldo = saldoService.saldos();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(saldo);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar saldos. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar saldos.");
        }
    }

}
