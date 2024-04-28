package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ProjetosService;
import com.smartSpd.smartSpding.Core.Dominio.Projetos;
import com.smartSpd.smartSpding.Core.Excecao.ProjetoInvalidoException;
import com.smartSpd.smartSpding.Core.Excecao.ReceitaNaoEncontradaException;
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
@RequestMapping("/projetos")
public class ProjetosController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ProjetosService projetosService;

    public ProjetosController(ProjetosService projetosService) {
        this.projetosService = projetosService;
    }

    @PostMapping("/registroProjetos")
    @Transactional
    public ResponseEntity<?> registroProjetos(@RequestBody @Valid Projetos data) {
        try {
            projetosService.salvarProjetos(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Projeto cadastrado com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar projeto. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar projeto.");
        }
    }

    @GetMapping("/buscarProjetos")
    @Transactional
    public ResponseEntity<?> buscarProjetos() {
        try {
            List<Projetos> projetos = projetosService.buscarTodosProjetos();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(projetos);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar projetos. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar projetos.");
        }
    }

    @PatchMapping("/editarProjeto")
    @Transactional
    public  ResponseEntity<?> editarProjeto(@RequestBody @Valid Projetos data) {
        try {
            projetosService.editarProjeto(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Projeto editado com sucesso.\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar projetos. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar projetos.");
        }
    }

    @DeleteMapping("/deletarProjeto/{id}")
    @Transactional
    public ResponseEntity<?> deletarProjeto(@PathVariable Long id) {
        try {
            projetosService.deletarProjeto(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Projeto deletado com sucesso.\"}");

        } catch (ProjetoInvalidoException e) {
            log.warning("Projeto com ID " + id + " não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Despesa não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar projeto.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar projeto.");
        }
    }

    @GetMapping("/buscarProjetoInvidual/{id}")
    @Transactional
    public ResponseEntity<?> buscarProjetoInvidual(@PathVariable Long id) {
        try {
            List<Projetos> projeto = projetosService.buscarProjetoPorId(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(projeto);

        } catch (ProjetoInvalidoException e) {
            log.warning("Projeto com ID " + id + " não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("{\"message\": \"Despesa não encontrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar projeto.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar projeto.");
        }
    }

   /* @PostMapping("/conselhoPorBalanco")
    @Transactional
    public ResponseEntity<?> registroBalancoRapido(@RequestBody @Valid BalancoRapidoDTO balancoRapidoDTO) {
        try {
            List<String> balanco = conselhosService.conselhosPorBalanco(balancoRapidoDTO);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(balanco);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao gerar conselho. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao gerar conselho.");
        }
    }*/
}