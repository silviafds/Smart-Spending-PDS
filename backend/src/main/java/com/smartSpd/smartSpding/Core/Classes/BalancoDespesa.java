package com.smartSpd.smartSpding.Core.Classes;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@Data
@SqlResultSetMapping(
        name = "BalancoDespesaMapping",
        classes = @ConstructorResult(
                targetClass = BalancoDespesa.class,
                columns = {
                        @ColumnResult(name = "categoria_transacao", type = String.class),
                        @ColumnResult(name = "quantidade_transacao", type = Long.class)
                }
        )
)
public class BalancoDespesa {
    private String categoria_transacao;
    private Long quantidade_transacao;

    public BalancoDespesa(String categoria_transacao, Long quantidade_transacao) {
        this.categoria_transacao = categoria_transacao;
        this.quantidade_transacao = quantidade_transacao;
    }
}
