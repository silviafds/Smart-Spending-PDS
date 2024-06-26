package com.smartSpd.smartSpding.Core.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalancoCategoriaDTO {
    private String nomeCategoria;
    private double valorGasto;
    private double valorInvestimento;
}
