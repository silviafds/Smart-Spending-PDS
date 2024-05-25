package com.smartSpd.smartSpding.Core.Enum;

public enum BalancoEnum {
    DESPESA("Despesa"),
    RECEITA("Receita"),
    DESPESA_RECEITA("Despesa e Receita");

    private String tiposBalanco;

    BalancoEnum(String tiposBalanco) {
        this.tiposBalanco = tiposBalanco;
    }

    public String getBalanco() {
        return tiposBalanco;
    }
}