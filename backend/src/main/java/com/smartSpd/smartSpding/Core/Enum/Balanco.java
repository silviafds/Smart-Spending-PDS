package com.smartSpd.smartSpding.Core.Enum;

public enum Balanco {
    DESPESA("Despesa"),
    RECEITA("Receita");

    private String tiposBalanco;

    Balanco(String tiposBalanco) {
        this.tiposBalanco = tiposBalanco;
    }

    public String getBalanco() {
        return tiposBalanco;
    }
}
