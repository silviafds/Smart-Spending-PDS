package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.CategoriaService;
import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaException;
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
@RequestMapping("/categoria")
public class CategoriaController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/registroCategoria")
    @Transactional
    public ResponseEntity<?> registroCategoria(@RequestBody @Valid CategoriaDTO data) {
        try {
            categoriaService.salvarCategoria(data);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Categoria cadastrada.");

        } catch (CategoriaException e) {
            log.log(Level.SEVERE, "Campos obrigatórios da categoria não foram preenchidos. ", e);
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Dados inválidos. Preencha todos os campos obrigatórios da receita.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar categoria. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar categoria.");
        }
    }

    @PatchMapping("/editarCategoria")
    @Transactional
    public ResponseEntity<String> editarCategoria(@RequestBody @Valid CategoriaDTO data) {
        try {
            categoriaService.editarCategoria(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Categoria atualizada com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar categoria.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao editar categoria.");
        }
    }

    @DeleteMapping("/deletarCategoriaDespesa/{id}")
    @Transactional
    public ResponseEntity<String> deletarCategoriaDespesa(@PathVariable Long id) {
        try {
            categoriaService.deletarCategoria(id, "Despesa");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Categoria deletada com sucesso.\"}");

        } catch (CategoriaException e) {
            log.warning("Categoria com ID " + id + " não encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Categoria não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao categoria receita.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar categoria.");
        }
    }

    @DeleteMapping("/deletarCategoriaReceita/{id}")
    @Transactional
    public ResponseEntity<String> deletarCategoriaReceita(@PathVariable Long id) {
        try {
            categoriaService.deletarCategoria(id, "Receita");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Categoria deletada com sucesso.\"}");

        } catch (CategoriaException e) {
            log.warning("Categoria com ID " + id + " não encontrada.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Categoria não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao categoria receita.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar categoria.");
        }
    }

    @GetMapping("/buscarCategoriaReceita")
    @Transactional
    public ResponseEntity<?> buscarCategoriaReceita() {
        try {
            List<CategoriaReceita> contas = categoriaService.buscarTodasCategoriasReceitas();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(contas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar categoria de receitas. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de receitas.");
        }
    }

    @GetMapping("/buscarCategoriaDespesa")
    @Transactional
    public ResponseEntity<?> buscarCategoriaDespesa() {
        try {
            List<CategoriaDespesa> contas = categoriaService.buscarTodasCategoriasDespesa();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(contas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar categoria de despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de despesa.");
        }
    }

    @GetMapping("/buscarCategoriaReceitaPorId/{id}")
    @Transactional
    public ResponseEntity<?> buscarCategoriaReceitaPorId(@PathVariable Long id) {
        try {
            List<?> categoriaReceita = categoriaService.buscarCategoriaReceitaPorID(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoriaReceita);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar categoria de receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de receita.");
        }
    }

    @GetMapping("/buscarCategoriaDespesaPorId/{id}")
    @Transactional
    public ResponseEntity<?> buscarCategoriaDespesaPorId(@PathVariable Long id) {
        try {
            List<?> categoriaDespesa = categoriaService.buscarCategoriaDespesaPorID(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoriaDespesa);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar categoria de despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar categoria de despesa.");
        }
    }

}