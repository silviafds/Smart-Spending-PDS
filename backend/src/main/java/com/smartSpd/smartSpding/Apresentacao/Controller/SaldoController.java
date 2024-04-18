package com.smartSpd.smartSpding.Apresentacao.Controller;


import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Core.DTO.SaldoDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class SaldoController {

    private final SaldoService saldoService;

    public SaldoController(SaldoService saldoService) {
        this.saldoService = saldoService;
    }

    @GetMapping("/{idContaInterna}")
    public ResponseEntity getSaldoPorContaInterna(@PathVariable Long idContaInterna) {
        Double saldoTotal = saldoService.calcularSaldoPorContaInterna(idContaInterna);

        if (saldoTotal != null) {
            SaldoDTO saldoDTO = new SaldoDTO();
            saldoDTO.setIdContaInterna(idContaInterna);
            saldoDTO.setSaldoTotal(saldoTotal);

            return ResponseEntity.ok(saldoDTO);
        }

        String mensagem = "Saldo não encontrado na conta interna de ID: " + idContaInterna;
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }

}
