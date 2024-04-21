package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
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

import static com.smartSpd.smartSpding.Core.Enum.Balanco.DESPESA;
import static com.smartSpd.smartSpding.Core.Enum.Balanco.RECEITA;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.*;

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

    @GetMapping("/buscarConfiguracaoConselhos")
    @Transactional
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
    }
}