package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
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
@RequestMapping("/dashboard")
public class DashboardController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @PostMapping("/adicionarBalancoDashboard")
    @Transactional
    public ResponseEntity<?> adicionarBalancoDashboard(@RequestBody @Valid DashDTO data) {
        try {
            String mensagem = dashboardService.salvarBalancoDashboard(data.identicador_balanco());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mensagem);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dashboards.");
        }
    }

    @GetMapping("/buscarBalancosDashboard")
    @Transactional
    public ResponseEntity<?> buscarBalancosDashboard() {
        try {
            List<List<?>> dashboards = dashboardService.buscarBalancosDashboard();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dashboards);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dashboards.");
        }
    }
    
    @DeleteMapping("/deletarBalancoDashboard")
    @Transactional
    public ResponseEntity<?> deletarBalancoDashboard(@RequestBody @Valid DashDTO data) {
        try {
            String mensagem = dashboardService.deletarBalancoDashboard(data.identicador_balanco());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mensagem);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar o balanço do dashboard. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o balanço do dashboard.");
        }
    }
}
