package com.smartSpd.smartSpding.Core.DTO;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;

import java.time.LocalDate;

public class DespesaDTO {
    private Long id;
    private ContaInterna contaInterna;
    private String categoria;
    private String titulo_contabil;
    private LocalDate dataDespesa;
    private Double valorDespesa;
    private String categoriaTransacao;
    private String bancoOrigem;
    private String dadosBancariosOrigem;
    private String beneficiario;
    private String bancoDestino;
    private String agenciaDestino;
    private String numeroContaDestino;
    private String descricao;
    private Long identificadorProjeto;


    public DespesaDTO(Long id, ContaInterna contaInterna, String categoria, String titulo_contabil,
                      LocalDate dataDespesa, Double valorDespesa, String categoriaTransacao, String bancoOrigem,
                      String dadosBancariosOrigem, String beneficiario, String bancoDestino, String agenciaDestino,
                      String numeroContaDestino, String descricao, Long identificadorProjeto) {
        this.id = id;
        this.contaInterna = contaInterna;
        this.categoria = categoria;
        this.titulo_contabil = titulo_contabil;
        this.dataDespesa = dataDespesa;
        this.valorDespesa = valorDespesa;
        this.categoriaTransacao = categoriaTransacao;
        this.bancoOrigem = bancoOrigem;
        this.dadosBancariosOrigem = dadosBancariosOrigem;
        this.beneficiario = beneficiario;
        this.bancoDestino = bancoDestino;
        this.agenciaDestino = agenciaDestino;
        this.numeroContaDestino = numeroContaDestino;
        this.descricao = descricao;
        this.identificadorProjeto = identificadorProjeto;
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

    public LocalDate getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(LocalDate dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public Double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public String getCategoriaTransacao() {
        return categoriaTransacao;
    }

    public void setCategoriaTransacao(String categoriaTransacao) {
        this.categoriaTransacao = categoriaTransacao;
    }

    public String getBancoOrigem() {
        return bancoOrigem;
    }

    public void setBancoOrigem(String bancoOrigem) {
        this.bancoOrigem = bancoOrigem;
    }

    public String getDadosBancariosOrigem() {
        return dadosBancariosOrigem;
    }

    public void setDadosBancariosOrigem(String dadosBancariosOrigem) {
        this.dadosBancariosOrigem = dadosBancariosOrigem;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public String getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public String getNumeroContaDestino() {
        return numeroContaDestino;
    }

    public void setNumeroContaDestino(String numeroContaDestino) {
        this.numeroContaDestino = numeroContaDestino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdentificadorProjeto() {
        return identificadorProjeto;
    }

    public void setIdentificadorProjeto(Long identificadorProjeto) {
        this.identificadorProjeto = identificadorProjeto;
    }
}