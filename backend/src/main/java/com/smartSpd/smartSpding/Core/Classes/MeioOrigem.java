package com.smartSpd.smartSpding.Core.Classes;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@SqlResultSetMapping(
        name = "MeioOrigemMapping",
        classes = @ConstructorResult(
                targetClass = MeioOrigem.class,
                columns = {
                        @ColumnResult(name = "dado", type = String.class),
                        @ColumnResult(name = "valor", type = Long.class)
                }
        )
)
public class MeioOrigem {
    private String dado;
    private Long valor;

    @Transient
    private String nome;
    @Transient
    private String tipoOrigem;
    @Transient
    private LocalDate dataInicio;
    @Transient
    private LocalDate dataTermino;
    @Transient
    private String tipoVisualizacao;

    public MeioOrigem(String dado, Long valor) {
        this.dado = dado;
        this.valor = valor;
    }

    public MeioOrigem(String nome, String tipoOrigem, LocalDate dataInicio, LocalDate dataTermino, String tipoVisualizacao) {
        this.nome = nome;
        this.tipoOrigem = tipoOrigem;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.tipoVisualizacao = tipoVisualizacao;
    }
}