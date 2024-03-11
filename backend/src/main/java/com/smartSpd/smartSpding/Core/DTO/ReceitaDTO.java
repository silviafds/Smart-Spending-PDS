package com.smartSpd.smartSpding.Core.DTO;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;

import java.time.LocalDate;

public class ReceitaDTO {
    private Long id;
    private ContaInterna contaInterna;
    private String categoria;
    private String titulo_contabil;
    private LocalDate dataReceita;
    private Double valorReceita;
    private String origem;
    private String bancoOrigem;
    private String agenciaOrigem;
    private String numeroContaOrigem;
    private String bancoDestino;
    private String dadosBancariosDestino;
    private String descricao;

    public ReceitaDTO(Long id, ContaInterna contaInterna, String categoria, String titulo_contabil, LocalDate dataReceita,
                      Double valorReceita, String origem, String bancoOrigem, String agenciaOrigem,
                      String numeroContaOrigem, String bancoDestino, String dadosBancariosDestino, String descricao) {
        this.id = id;
        this.contaInterna = contaInterna;
        this.categoria = categoria;
        this.titulo_contabil = titulo_contabil;
        this.dataReceita = dataReceita;
        this.valorReceita = valorReceita;
        this.origem = origem;
        this.bancoOrigem = bancoOrigem;
        this.agenciaOrigem = agenciaOrigem;
        this.numeroContaOrigem = numeroContaOrigem;
        this.bancoDestino = bancoDestino;
        this.dadosBancariosDestino = dadosBancariosDestino;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContaInterna getContaInterna() {
        return contaInterna;
    }

    public void setContaInterna(ContaInterna contaInterna) {
        this.contaInterna = contaInterna;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo_contabil() {
        return titulo_contabil;
    }

    public void setTitulo_contabil(String titulo_contabil) {
        this.titulo_contabil = titulo_contabil;
    }

    public LocalDate getDataReceita() {
        return dataReceita;
    }

    public void setDataReceita(LocalDate dataReceita) {
        this.dataReceita = dataReceita;
    }

    public Double getValorReceita() {
        return valorReceita;
    }

    public void setValorReceita(Double valorReceita) {
        this.valorReceita = valorReceita;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getBancoOrigem() {
        return bancoOrigem;
    }

    public void setBancoOrigem(String bancoOrigem) {
        this.bancoOrigem = bancoOrigem;
    }

    public String getAgenciaOrigem() {
        return agenciaOrigem;
    }

    public void setAgenciaOrigem(String agenciaOrigem) {
        this.agenciaOrigem = agenciaOrigem;
    }

    public String getNumeroContaOrigem() {
        return numeroContaOrigem;
    }

    public void setNumeroContaOrigem(String numeroContaOrigem) {
        this.numeroContaOrigem = numeroContaOrigem;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public String getDadosBancariosDestino() {
        return dadosBancariosDestino;
    }

    public void setDadosBancariosDestino(String dadosBancariosDestino) {
        this.dadosBancariosDestino = dadosBancariosDestino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
