package com.smartSpd.smartSpding.Core.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalancoRespostaDTO {
    private String nome;
    private String tipoBalanco;
    private String analiseBalanco;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String tipoVisualizacao;
    private String categoriaOuTituloContabil;
}
