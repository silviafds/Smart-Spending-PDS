package com.smartSpd.smartSpding.Core.Enum;

public enum MetodosPagamento {
    PIX("Pix"),
    CHEQUE("Cheque"),
    TRANSFERENCIA("Transferência"),
    PAPEL_E_MOEDA("Papel e moeda");

    private String meiosPagamento;

    MetodosPagamento(String meiosPagamento) {
        this.meiosPagamento = meiosPagamento;
    }

    public String getMeiosPagamento() {
        return meiosPagamento;
    }
}