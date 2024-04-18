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
@Table(name = "receita")
@Entity(name = "receita")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoria;
    private String titulo_contabil;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate dataReceita;
    private Double valorReceita;
    private String pagador;
    private String origem;
    private String bancoOrigem;
    private String agenciaOrigem;
    private String numeroContaOrigem;
    private String bancoDestino;
    private String agenciaDestino;
    private String numeroContaDestino;
    private String descricao;
    private String tipoContaDestino;

    @ManyToOne
    @JoinColumn(name = "conta_interna_id")
    private ContaInterna contaInterna;

    @Transient
    private String dataFormatada;

    @Transient
    private String dadosBancarios;

    public Receita(String categoria, String titulo_contabil, LocalDate dataReceita, Double valorReceita,
                   String origem, String bancoOrigem, String agenciaOrigem, String numeroContaOrigem, String bancoDestino,
                   String agenciaDestino, String numeroContaDestino, String descricao, ContaInterna contaInterna,
                   String tipoContaDestino) {
        this.categoria = categoria;
        this.titulo_contabil = titulo_contabil;
        this.dataReceita = dataReceita;
        this.valorReceita = valorReceita;
        this.origem = origem;
        this.bancoOrigem = bancoOrigem;
        this.agenciaOrigem = agenciaOrigem;
        this.numeroContaOrigem = numeroContaOrigem;
        this.bancoDestino = bancoDestino;
        this.agenciaDestino = agenciaDestino;
        this.numeroContaDestino = numeroContaDestino;
        this.descricao = descricao;
        this.contaInterna = contaInterna;
        this.tipoContaDestino = tipoContaDestino;
    }

}
