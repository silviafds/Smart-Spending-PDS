package com.smartSpd.smartSpding.Core.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalancoRapidoDTO {
    private String tipoEstabelecimento;
    private String nome;
    private String tipoBalanco;
    private String analiseBalanco;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String tipoVisualizacao;
    private String categoriaOuTituloContabil;
    private List<BalancoCategoriaDTO> categorias;
}
