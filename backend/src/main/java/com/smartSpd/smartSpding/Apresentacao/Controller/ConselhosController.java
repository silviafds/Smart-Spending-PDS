package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.web.bind.annotation.*;

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
    @Transactional
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
}