package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ContaBancariaService;
import com.smartSpd.smartSpding.Core.Classes.Bancos;
import com.smartSpd.smartSpding.Core.Classes.DadosBancarios;
import com.smartSpd.smartSpding.Core.DTO.ContaBancariaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaBancaria;
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
@RequestMapping("/contaBancaria")
public class ContaBancariaController {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ContaBancariaService contaBancariaService;

    public ContaBancariaController(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }

    @PostMapping("/registrarContaBancaria")
    public ResponseEntity register(@RequestBody @Valid ContaBancariaDTO data) {
        try {
            boolean contaCriada = contaBancariaService.cadastrarContaBancaria(data);
            String mensagem = contaCriada ? "Conta Bancária cadastrada." : "Erro ao cadastrar Conta Bancária.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar Conta Bancária.");
        }
    }

    @PatchMapping("/editarContaBancaria")
    public ResponseEntity editarContaBancaria(@RequestBody @Valid ContaBancariaDTO data) {
        try {
            boolean contaCriada = contaBancariaService.editarContaBancaria(data);
            String mensagem = contaCriada ? "Conta Bancária atualizada com sucesso." : "Conta Bancária não atualizada.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar Conta Bancária.");
        }
    }

    @GetMapping("/buscarContaBancaria")
    public ResponseEntity buscarContaBancaria() {
        try {
            List<ContaBancaria> receitas = contaBancariaService.buscarContaBacaria();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(receitas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar Conta Bancária. ");
        }
    }

    @DeleteMapping("/deletarContaBancaria/{id}")
    public ResponseEntity deletarContaBancaria(@PathVariable int id) {
        try {
            boolean contaBancaria = contaBancariaService.deletarContaBacaria(id);
            String mensagem = contaBancaria ? "Conta Bancária deletada com sucesso." : "Conta Bancária não deletada.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar Conta Bancária.");
        }
    }

    @GetMapping("/buscarContaBancariaPorId/{id}")
    public ResponseEntity buscarContaBancariaPorId(@PathVariable int id) {
        try {
            List<ContaBancaria> receitas = contaBancariaService.buscarContaBancariaPorId(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(receitas);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar Conta Bancária de id: "+id);
        }
    }

    @GetMapping("/buscarContaBancariaPorNome")
    public ResponseEntity buscarBancoPorNome() {
        try {
            List<Bancos> bancos = contaBancariaService.buscarBancoPeloNome();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bancos);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar Conta Bancária de id: ");
        }
    }

    @GetMapping("/buscarDadosBancariosPorBanco/{banco}")
    public ResponseEntity buscarDadosBancariosPorBanco(@PathVariable String banco) {
        try {
            List<DadosBancarios> bancos = contaBancariaService.buscarDadosBancariosPorBanco(banco);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bancos);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Falha ao buscar Conta Bancária. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar Conta Bancária de id: ");
        }
    }
}
