package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.Dominio.Origem;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.smartSpd.smartSpding.Aplicacao.casoDeUso.OrigemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/origem")
public class OrigemController {

    private final OrigemService origemService;

    public OrigemController(OrigemService origemService) {
        this.origemService = origemService;
    }

    @GetMapping("/buscarOrigem")
    @Transactional
    public ResponseEntity buscarConta() {
        List<Origem> origem = origemService.buscarTodasOrigens();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(origem);
    }

}
