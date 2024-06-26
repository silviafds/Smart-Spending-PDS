package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
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
@RequestMapping("/conselhos")
public class ConselhosController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ConselhosService conselhosService;

    public ConselhosController(ConselhosService conselhosService) {
        this.conselhosService = conselhosService;
    }

    @PostMapping("/registroConselho")
    public ResponseEntity<?> registroConselho(@RequestBody @Valid ConselhosDTO data) {
        try {
            boolean statusConselho = conselhosService.salvarConselhos(data);
            String mensagem = statusConselho ? "Conselho casdastrado com sucesso." : "Erro ao cadastrar status.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar conselho. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar conselho.");
        }
    }

    @PostMapping("/registroConselhoFramework")
    public ResponseEntity<?> registroConselhoFramework(@RequestBody @Valid ConselhosDTO data) {
        try {
            boolean statusConselho = conselhosService.salvarConselhos(data);
            String mensagem = statusConselho ? "Conselho casdastrado com sucesso." : "Erro ao cadastrar status.";

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + mensagem + "\"}");

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar conselho. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar conselho.");
        }
    }

    @GetMapping("/buscarConfiguracaoConselhos")
    public ResponseEntity<?> registroConselho() {
        try {
            List<Conselhos> conselho = conselhosService.buscarConfiguracaoConselhos();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(conselho);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar configuração de conselho. ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar configuração de conselho.");
        }
    }

    @PostMapping("/conselhoPorBalanco")
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
    }
}