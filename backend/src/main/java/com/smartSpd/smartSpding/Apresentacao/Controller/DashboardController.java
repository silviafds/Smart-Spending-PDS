package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import com.smartSpd.smartSpding.Core.Excecao.DashboardNaoEncontradoException;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping("/criarDashboard")
    @Transactional
    public ResponseEntity<?> createDashboard(@RequestBody @Valid Dashboard dashboard) {
        try {
            dashboardService.createDashboard(dashboard.getNome(), dashboard.getBalancos());
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dashboard criado com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao criar dashboard. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar dashboard.");
        }
    }

    @GetMapping("/buscarDashboard")
    @Transactional
    public ResponseEntity<?> buscarDashboard() {
        try {
            Dashboard dashboards = dashboardService.buscarDashboard();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dashboards);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dashboards.");
        }
    }

    @DeleteMapping("/deletarDashboard")
    @Transactional
    public ResponseEntity<?> deletarDashboard() {
        try {
            dashboardService.deletarDashboard();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dashboard deletado com sucesso.\"}");
        } catch (DashboardNaoEncontradoException e) {
            log.warning("Dashboard não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dashboard não encontrado.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar dashboard.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar dashboard.");
        }
    }

    @PostMapping("/adicionarBalanco")
    @Transactional
    public ResponseEntity<?> adicionarBalanco(@PathVariable @RequestBody @Valid Balancos balanco) {
        try {
            dashboardService.addBalanco(balanco);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço adicionado ao dashboard com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao adicionar balanço ao dashboard. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar balanço ao dashboard.");
        }
    }

    @DeleteMapping("/removerBalanco")
    @Transactional
    public ResponseEntity<?> removerBalanco(@PathVariable Balancos balanco) {
        try {
            dashboardService.removeBalanco(balanco);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Balanço removido do dashboard com sucesso.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao remover balanço do dashboard. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao remover balanço do dashboard.");
        }
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
            List<BalancoDespesaReceita> dashboards = dashboardService.buscarBalancosDashboard();
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
