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
@Table(name = "despesa")
@Entity(name = "despesa")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoria;
    private String titulo_contabil;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate dataDespesa;
    private Double valorDespesa;
    private String categoriaTransacao;
    private String bancoOrigem;
    private String tipoContaOrigem;
    private String agenciaOrigem;
    private String numeroContaOrigem;
    private String beneficiario;
    private String bancoDestino;
    private String agenciaDestino;
    private String numeroContaDestino;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "conta_interna_id")
    private ContaInterna contaInterna;

    @Transient
    private String dataFormatada;

    @Transient
    private String dadosBancarios;

    public Despesa(String categoria, String titulo_contabil, LocalDate dataDespesa, Double valorDespesa,
                   String categoriaTransacao, String bancoOrigem, String tipoContaOrigem, String agenciaOrigem,
                   String numeroContaOrigem, String beneficiario, String bancoDestino, String agenciaDestino,
                   String numeroContaDestino, String descricao, ContaInterna contaInterna, String dataFormatada,
                   String dadosBancarios) {
        this.categoria = categoria;
        this.titulo_contabil = titulo_contabil;
        this.dataDespesa = dataDespesa;
        this.valorDespesa = valorDespesa;
        this.categoriaTransacao = categoriaTransacao;
        this.bancoOrigem = bancoOrigem;
        this.tipoContaOrigem = tipoContaOrigem;
        this.agenciaOrigem = agenciaOrigem;
        this.numeroContaOrigem = numeroContaOrigem;
        this.beneficiario = beneficiario;
        this.bancoDestino = bancoDestino;
        this.agenciaDestino = agenciaDestino;
        this.numeroContaDestino = numeroContaDestino;
        this.descricao = descricao;
        this.contaInterna = contaInterna;
        this.dataFormatada = dataFormatada;
        this.dadosBancarios = dadosBancarios;
    }
}
