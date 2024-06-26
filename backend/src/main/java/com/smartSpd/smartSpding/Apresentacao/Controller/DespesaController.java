package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaService;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Core.Excecao.DespesaInvalidaException;
import com.smartSpd.smartSpding.Core.Excecao.DespesaNaoEncontradaException;
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
@RequestMapping("/despesa")
public class DespesaController {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping("/registrarDespesa")
    public ResponseEntity<String> register(@RequestBody @Valid DespesaDTO data) {
        try {
            despesaService.cadastrarDespesa(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Despesa registrada.\"}");

        } catch (DespesaInvalidaException e) {
            log.log(Level.SEVERE, "Campos obrigatórios da despesa não foram preenchidos. ", e);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dados inválidos. Preencha todos os campos obrigatórios da despesa.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar nova despesa.");
        }
    }

    @PatchMapping("/editarDespesa")
    public ResponseEntity<String> editarDespesa(@RequestBody @Valid DespesaDTO data) {
        try {
            despesaService.editarDespesa(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Receita atualizada com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar despesa.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar despesa.");
        }
    }
    @DeleteMapping("/deletarDespesa/{id}")
    public ResponseEntity<String> deletarDespesa(@PathVariable Long id) {
        try {
            despesaService.deletarDespesa(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Despesa deletada com sucesso.\"}");
        } catch (DespesaNaoEncontradaException e) {
            log.warning("Despesa com ID " + id + " não encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Despesa não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar despesa.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar despesa.");
        }
    }

    @GetMapping("/buscarCategoriaDespesa")
    public ResponseEntity<?> buscarCategoriaDespesa() {
        try {
            List<CategoriaDespesa> contas = despesaService.buscarTodasCategoriasDespesa();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(contas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar categoria de despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de despesa.");
        }
    }

    @GetMapping("/buscarDespesa")
    public ResponseEntity<?> buscarDespesa() {
        try {
            List<Despesa> despesas = despesaService.buscarTodasAsDespesas();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(despesas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar despesas. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar despesas. ");
        }
    }

    @GetMapping("/buscarDespesasPorId/{id}")
    public ResponseEntity<?> buscarDepesasPorId(@PathVariable Integer id) {
        try {
            List<Despesa> despesas = despesaService.buscarDespesasPorId(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(despesas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar receita de id: "+id);
        }
    }

    @GetMapping("/buscarTituloContabilDespesa/{id}")
    public ResponseEntity<?> buscarTituloContabilDespesa(@PathVariable Integer id) {
        try {
            List<?> titulos = despesaService.buscarTodosTitulosContabeisDespesa(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(titulos);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar título contábil. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar título contábil de id: "+id);
        }
    }

}