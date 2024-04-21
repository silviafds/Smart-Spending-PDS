package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/saldo")
public class SaldoController {

    private final SaldoService saldoService;

    @Autowired
    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }


    @GetMapping("/calcularSaldos")
    @Transactional
    public ResponseEntity<Map<String, Object>> calcularSaldoPorContaHabilitada() {
        Map<Long, Double> saldosPorConta = saldoService.calcularSaldoPorContaHabilitada();
        Double saldoTotal = saldoService.calcularSaldoTotal();

        Map<String, Object> saldos = new HashMap<>();
        saldos.put("saldosPorConta", saldosPorConta);
        saldos.put("saldoTotal", saldoTotal);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saldos);
    }

}
