package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ReceitaService;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import com.smartSpd.smartSpding.Core.Excecao.ReceitaInvalidaException;
import com.smartSpd.smartSpding.Core.Excecao.ReceitaNaoEncontradaException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/receita")
public class ReceitaController {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));
    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping("/registrarReceita")
    public ResponseEntity<String> register(@RequestBody @Valid ReceitaDTO data) {
        try {
            receitaService.cadastrarReceita(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Receita registrada.\"}");

        } catch (ReceitaInvalidaException e) {
            log.log(Level.SEVERE, "Campos obrigatórios da receita não foram preenchidos. ", e);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dados inválidos. Preencha todos os campos obrigatórios da receita.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar nova receita.");
        }
    }

    @PatchMapping("/editarReceita")
    public ResponseEntity<String> editarReceita(@RequestBody @Valid ReceitaDTO data) {
        try {
            receitaService.editarReceita(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Receita atualizada com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar receita.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar receita.");
        }
    }
    @DeleteMapping("/deletarReceita/{id}")
    public ResponseEntity<String> deletarReceita(@PathVariable Long id) {
        try {
            receitaService.deletarReceita(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Receita deletada com sucesso.\"}");

        } catch (ReceitaNaoEncontradaException e) {
            log.warning("Receita com ID " + id + " não encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Despesa não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar receita.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar receita.");
        }
    }

    @GetMapping("/buscarCategoriaReceita")
    public ResponseEntity<?> buscarCategoriaReceita() {
        try {
            List<CategoriaReceita> contas = receitaService.buscarTodasCategoriasReceitas();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(contas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar categoria de receitas. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de receitas.");
        }
    }

    @GetMapping("/buscarReceitas")
    public ResponseEntity<?> buscarReceitas() {
        try {
            List<Receita> receitas = receitaService.buscarTodasAsReceitas();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(receitas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar receitas. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar receitas. ");
        }
    }

    @GetMapping("/buscarReceitasPorId/{id}")
    public ResponseEntity<?> buscarReceitasPorId(@PathVariable Integer id) {
        try {
            List<Receita> receitas = receitaService.buscarReceitasPorId(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(receitas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar receita de id: "+id);
        }
    }

    @GetMapping("/buscarTituloContabilReceita/{id}")
    public ResponseEntity<?> buscarTituloContabilReceita(@PathVariable Integer id) {
        try {
            List<TituloContabilReceita> titulos = receitaService.buscarTodosTitulosContabeisReceitas(id);
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