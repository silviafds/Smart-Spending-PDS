package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ContaInternaService;
import com.smartSpd.smartSpding.Core.DTO.ContaInternaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/contaInterna")
public class contaInternaController {

    private final ContaInternaService contaInternaService;

    public contaInternaController(ContaInternaService contaInternaService) {
        this.contaInternaService = contaInternaService;
    }

    @PostMapping("/registrarConta")
    @Transactional
    public ResponseEntity register(@RequestBody @Valid ContaInternaDTO data) {
        boolean contaCriada = contaInternaService.cadastrarContaInterna(data);
        String mensagem = contaCriada ? "Registro de Conta Interna realizado com sucesso." : "Conta Interna já existe.";

        if(contaCriada) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }

    @PatchMapping("/editarConta")
    @Transactional
    public ResponseEntity editarConta(@RequestBody @Valid ContaInternaDTO data) {
        boolean contaCriada = contaInternaService.editarContaInterna(data);
        String mensagem = contaCriada ? "Conta atualizada com sucesso." : "Conta Interna não atualizada.";

        if(contaCriada) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }

    @PatchMapping("/editarStatusConta")
    @Transactional
    public ResponseEntity editarStausConta(@RequestBody @Valid ContaInternaDTO data) {
        boolean contaCriada = contaInternaService.editarStatusContaInterna(data);
        String mensagem = contaCriada ? "Conta atualizada com sucesso." : "Conta Interna não atualizada.";

        if(contaCriada) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }

    @DeleteMapping("/deletarConta/{id}")
    @Transactional
    public ResponseEntity deletarConta(@PathVariable int id) {
        boolean contaCriada = contaInternaService.deletarContaInterna(id);
        String mensagem = contaCriada ? "Conta deletada com sucesso." : "Existem dados vinculados a esta conta.";
        if(contaCriada) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");
        }

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }

    @GetMapping("/buscarConta")
    @Transactional
    public ResponseEntity buscarConta() {
        List<ContaInterna> contas = contaInternaService.buscarTodasContasInterna();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contas);
    }

    @GetMapping("/buscarContaHabilitadas")
    @Transactional
    public ResponseEntity buscarContaHabilitada() {
        List<ContaInterna> contas = contaInternaService.buscarTodasContasInternaHabilitadas();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(contas);
    }

    @GetMapping("/buscarContaInvidual/{id}")
    @Transactional
    public ResponseEntity buscarContaPeloId(@PathVariable int id) {
        ContaInterna contas = contaInternaService.buscarContaInternaPeloId(id);

        if (contas != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(contas);
        }

        String mensagem = "Conta Interna não deletada.";
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"" + mensagem + "\"}");
    }
}
