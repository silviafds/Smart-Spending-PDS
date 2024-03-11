package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Aplicacao.casoDeUso.ReceitaService;
import com.smartSpd.smartSpding.Core.DTO.EditarReceitaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ResponseEntity register(@RequestBody @Valid ReceitaDTO data) {
        try {
            boolean contaCriada = receitaService.cadastrarReceita(data);
            String mensagem = contaCriada ? "Receita registrada." : "Receita nãom registrada.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar nova receita receita.");
        }
    }

    @PatchMapping("/editarReceita")
    @Transactional
    public ResponseEntity editarReceita(@RequestBody @Valid ReceitaDTO data) {
        try {
            boolean contaCriada = receitaService.editarReceita(data);
            String mensagem = contaCriada ? "Conta atualizada com sucesso." : "Conta Interna não atualizada.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao atualizar receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar receita.");
        }
    }

    @DeleteMapping("/deletarReceita")
    @Transactional
    public ResponseEntity deletarReceita(@RequestBody @Valid EditarReceitaDTO data) {
        try {
            boolean contaCriada = receitaService.deletarReceita(data);
            String mensagem = contaCriada ? "Receita deletada com sucesso." : "Receita não deletada.";
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar receita.");
        }
    }

    @GetMapping("/buscarCategoriaReceita")
    @Transactional
    public ResponseEntity buscarCategoriaReceita() {
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

    /*@GetMapping("/buscarReceitas")
    @Transactional
    public ResponseEntity buscarReceitas() {
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
    }*/

    @GetMapping("/buscarReceitas")
    @Transactional
    public ResponseEntity buscarReceitas() {
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
    @Transactional
    public ResponseEntity buscarReceitasPorId(@PathVariable int id) {
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
    @Transactional
    public ResponseEntity buscarTituloContabilReceita(@PathVariable int id) {
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