package com.smartSpd.smartSpding.Core.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BalancoRapidoDespesaReceitaDTO {
    private String nome;
    private String tipoBalanco;
    private String analiseBalanco;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String tipoVisualizacao;
    private String categoriaOuTituloContabil;
}
