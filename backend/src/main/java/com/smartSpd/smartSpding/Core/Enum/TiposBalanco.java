package com.smartSpd.smartSpding.Core.Enum;

public enum TiposBalanco {
    BUSCAR_TODAS_DESPESAS("Listagem despesa por tempo"),
    BUSCAR_TODAS_RECEITAS("Listagem receita por tempo"),
    DESPESA_RECEITA("Despesa e Receita"),
    PROJETOS("Projetos");

    private String tiposBalanco;

    TiposBalanco(String tiposBalanco) {
        this.tiposBalanco = tiposBalanco;
    }

    public String getTiposBalanco() {
        return tiposBalanco;
    }

}
