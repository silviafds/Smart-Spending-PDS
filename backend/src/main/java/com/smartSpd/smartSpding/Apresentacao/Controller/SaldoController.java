package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/saldo")
public class SaldoController {

    private final SaldoService saldoService;

    @Autowired
    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @PostMapping("/calcularSaldoPorConta")
    public ResponseEntity<Map<Long, Double>> calcularSaldoPorContaHabilitada() {
        Map<Long, Double> saldos = saldoService.calcularSaldoPorContaHabilitada();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saldos);
    }

}
