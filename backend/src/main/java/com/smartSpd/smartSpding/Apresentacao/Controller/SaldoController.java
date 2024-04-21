package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;
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
    private final SaldoRepository saldoRepository;

    @Autowired
    public SaldoController(SaldoService saldoService, SaldoRepository saldoRepository) {
        this.saldoService = saldoService;
        this.saldoRepository = saldoRepository;
    }

    @GetMapping("/calcularSaldos")
    @Transactional
    public ResponseEntity<Map<String, Object>> calcularSaldoPorContaHabilitada() {
        Map<Long, Double> saldosPorConta = saldoService.calcularSaldoPorContaHabilitada();
        Map<String, Object> saldos = new HashMap<>();
        Double saldoTotal = saldoService.calcularSaldoTotal();

        saldos.put("Saldo Total", saldoTotal);

        for (Map.Entry<Long, Double> contaESaldo : saldosPorConta.entrySet()) {
            Long idConta = contaESaldo.getKey();
            Double saldo = contaESaldo.getValue();
            String nomeConta = saldoRepository.findNomeById(idConta);
            saldos.put(nomeConta, saldo);
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saldos);
    }

}
