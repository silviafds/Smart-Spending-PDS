package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
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
    public ResponseEntity<?> adicionarBalancoDashboard(@RequestBody @Valid DashDTO data) {
        try {
            String mensagem = dashboardService.salvarBalancoDashboard(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mensagem);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dashboards.");
        }
    }

    @GetMapping("/buscarBalancosDashboardProcessados")
    public ResponseEntity<?> buscarBalancosDashboardProcessados() {
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
    public ResponseEntity<?> deletarBalancoDashboard(@RequestBody @Valid DashDTO data) {
        try {
            String mensagem = dashboardService.deletarBalancoDashboard(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mensagem);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar o balanço do dashboard. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o balanço do dashboard.");
        }
    }

    @GetMapping("/listaBalancoDash")
    public ResponseEntity<?> listaBalancoDash() {
        try {
            List<Dash> dashboards = dashboardService.buscarBalancosNoDashboard();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dashboards);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dashboards.");
        }
    }
}
