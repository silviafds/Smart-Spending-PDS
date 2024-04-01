package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.CasoUso.MeioOrigemService;
import com.smartSpd.smartSpding.Core.Classes.MeioOrigem;
import com.smartSpd.smartSpding.Core.DTO.MeioOrigemDTO;
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
@RequestMapping("/meioOrigem")
public class IdentificacaoMeioOrigemController {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final MeioOrigemService meioOrigemService;

    public IdentificacaoMeioOrigemController(MeioOrigemService meioOrigemService) {
        this.meioOrigemService = meioOrigemService;
    }

    @PostMapping("/registroMeioOrigem")
    @Transactional
    public ResponseEntity<?> registroMeioOrigem(@RequestBody @Valid MeioOrigemDTO MeioOrigemDTO) {
        try {
            if(MeioOrigemDTO.getTipoBalanco().equals("Origem")) {
                List<MeioOrigem> meio = meioOrigemService.balancoMeiosPagamento(MeioOrigemDTO);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(meio);
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar meios de origem que mais recebem.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar meios de origem que mais recebem.");
        }
    }

}