package com.smartSpd.smartSpding.Core.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaldoDTO {
    private Long idContaInterna;
    private Double saldoTotal;

    public SaldoDTO(Long idContaInterna, Double saldoTotal) {
        this.idContaInterna = idContaInterna;
        this.saldoTotal = saldoTotal;
    }
}
