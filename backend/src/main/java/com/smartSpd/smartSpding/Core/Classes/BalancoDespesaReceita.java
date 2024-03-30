package com.smartSpd.smartSpding.Core.Classes;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@SqlResultSetMapping(
        name = "BalancoDespesaReceitaMapping",
        classes = @ConstructorResult(
                targetClass = BalancoDespesaReceita.class,
                columns = {
                        @ColumnResult(name = "categoriaOuTituloContabil", type = String.class),
                }
        )
)
public class BalancoDespesaReceita extends BalancoDespesa {

    @Transient
    private String categoriaOuTituloContabil;

    public BalancoDespesaReceita(String dado, Long valor, String categoriaOuTituloContabil) {
        super(dado, valor);
        this.categoriaOuTituloContabil = categoriaOuTituloContabil;
    }

    public BalancoDespesaReceita(String nome, String tipoBalanco, String analiseBalanco, LocalDate dataInicio, LocalDate dataTermino, String tipoVisualizacao, String categoriaOuTituloContabil) {
        super(nome, tipoBalanco, analiseBalanco, dataInicio, dataTermino, tipoVisualizacao);
        this.categoriaOuTituloContabil = categoriaOuTituloContabil;
    }
}
