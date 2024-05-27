package com.smartSpd.smartSpding.Core.Classes;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@SqlResultSetMapping(
        name = "BalancoDespesaMapping",
        classes = @ConstructorResult(
                targetClass = BalancoDespesa.class,
                columns = {
                        @ColumnResult(name = "dado", type = String.class),
                        @ColumnResult(name = "valor", type = Long.class)
                }
        )
)
public class BalancoDespesa {
    private String dado;
    private Long valor;

    @Transient
    private String nome;
    @Transient
    private String tipoBalanco;
    @Transient
    private String analiseBalanco;
    @Transient
    private LocalDate dataInicio;
    @Transient
    private LocalDate dataTermino;
    @Transient
    private String tipoVisualizacao;

    public BalancoDespesa(String dado, Long valor) {
        this.dado = dado;
        this.valor = valor;
    }

}