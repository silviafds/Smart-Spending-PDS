package com.smartSpd.smartSpding.Core.Enum;

public enum TiposBalanco {
    BUSCAR_TODAS_DESPESAS("Listagem despesa por tempo"),
    BUSCAR_TODAS_RECEITAS("Listagem receita por tempo"),
    DESPESA_RECEITA("Despesa e Receita"),
    PAGAMENTOS_MAIS_UTILIZADOS("Pagamentos mais utilizados"),
    ORIGENS_MAIS_RENTAVEIS("Origens mais rentáveis"),
    PROJETOS("Projetos"),
    MAQUINARIO_MAQUINARIO("Manutenção máquinario"),
    MAQUINARIO_COMPRADO("Maquinário comprado");

    private String tiposBalanco;

    TiposBalanco(String tiposBalanco) {
        this.tiposBalanco = tiposBalanco;
    }

    public String getTiposBalanco() {
        return tiposBalanco;
    }

}