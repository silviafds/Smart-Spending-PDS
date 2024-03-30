package com.smartSpd.smartSpding.Core.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BalancoRapidoDTO {
    private String nome;
    private String tipoBalanco;
    private String analiseBalanco;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String tipoVisualizacao;
    private String categoriaOuTituloContabil;
}
