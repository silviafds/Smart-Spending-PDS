package com.smartSpd.smartSpding.Core.DTO;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeioOrigemDTO {
    private String nome;
    private String tipoOrigem;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String tipoVisualizacao;
}
