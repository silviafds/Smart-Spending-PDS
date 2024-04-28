package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Setter
@Table(name = "projetos")
@Entity(name = "projetos")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Projetos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;
    private String valor_meta;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate data_inicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate data_final;

    private String descricao;

    private String valor_arrecadado_atual;

    public Projetos(Long id, String nome, String categoria, String valor_meta, LocalDate dataInicio,
                    LocalDate dataFinal, String descricao, String valor_arrecadado_atual) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.valor_meta = valor_meta;
        this.data_inicio = dataInicio;
        this.data_final = dataFinal;
        this.descricao = descricao;
        this.valor_arrecadado_atual = valor_arrecadado_atual;
    }
}
