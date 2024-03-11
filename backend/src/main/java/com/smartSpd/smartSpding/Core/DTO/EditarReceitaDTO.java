package com.smartSpd.smartSpding.Core.DTO;

import java.time.LocalDate;

public record EditarReceitaDTO(Long id, String contaInterna, String categoria, String titulo_contabil, LocalDate dataReceita, Double valorReceita,
                               String bancoReceita, Long agenciaReceita, Long contaReceita, String descricao) {
}
