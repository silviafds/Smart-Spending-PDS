package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Aplicacao.casoDeUso.DespesaService;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
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

@RestController
@RequestMapping("/despesa")
public class DespesaController {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @PostMapping("/registrarDespesa")
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid DespesaDTO data) {
        try {
            boolean despesaRegistrada = despesaService.cadastrarDespesa(data);
            if (despesaRegistrada) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Despesa registrada.\"}");
            }
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Despesa não registrada.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar nova despesa.");
        }
    }

    @PatchMapping("/editarDespesa")
    @Transactional
    public ResponseEntity<String> editarDespesa(@RequestBody @Valid DespesaDTO data) {
        try {
            boolean contaCriada = despesaService.editarDespesa(data);
            if(contaCriada) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Receita atualizada com sucesso.\"}");
            }
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Erro ao atualizar receita.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao atualizar receita. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar receita.");
        }
    }

    @DeleteMapping("/deletarDespesa/{id}")
    @Transactional
    public ResponseEntity<String> deletarDespesa(@PathVariable Long id) {
        try {
            boolean despesaDeletada = despesaService.deletarReceita(id);
            if(despesaDeletada) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"message\": \"Despesa deletada com sucesso.\"}");
            }
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"Houve um problema ao deletar a despesa.\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar despesa. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar despesa.");
        }
    }

    @GetMapping("/buscarCategoriaDespesa")
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
    public ResponseEntity<?> buscarTituloContabilDespesa(@PathVariable Integer id) {
        try {
            List<TituloContabilDespesa> titulos = despesaService.buscarTodosTitulosContabeisDespesa(id);
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
