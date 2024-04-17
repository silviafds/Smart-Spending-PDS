package com.smartSpd.smartSpding.Core.Dominio;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContaInternaComSaldo {
    private Long idConta;
    private String nomeConta;
    private BigDecimal saldoConta;

    public ContaInternaComSaldo(Long idConta, String nomeConta, BigDecimal saldoConta) {
        this.idConta = idConta;
        this.nomeConta = nomeConta;
        this.saldoConta = saldoConta;
    }

}
