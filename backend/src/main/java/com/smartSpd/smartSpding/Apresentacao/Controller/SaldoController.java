package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/saldo")
public class SaldoController {

    private final SaldoService saldoService;
    private final SaldoRepository saldoRepository;
    private static final Logger log = Logger.getLogger(SaldoController.class.getName());

    @Autowired
    public SaldoController(SaldoService saldoService, SaldoRepository saldoRepository) {
        this.saldoService = saldoService;
        this.saldoRepository = saldoRepository;
    }

    @GetMapping("/calcularSaldos")
    @Transactional
    public ResponseEntity<Map<String, Object>> calcularSaldoPorContaHabilitada() {
       try {
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
       } catch (Exception e) {
            log.severe("Erro ao calcular saldos: {}" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Erro ao calcular saldos"));
           // return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(saldos);

       }





    }

}
