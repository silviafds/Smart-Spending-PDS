package com.smartSpd.smartSpding.Core.Enum;

public enum TiposAnaliseBalanco {

    PAGAMENTOS_MAIS_UTILIZADOS("Pagamentos mais utilizados"),
    ORIGENS_MAIS_RENTAVEIS("Origens mais rentáveis");

    private String tiposAnaliseBalanco;

    TiposAnaliseBalanco(String tiposAnaliseBalanco) {
        this.tiposAnaliseBalanco = tiposAnaliseBalanco;
    }

    public String getTiposAnaliseBalanco() {
        return tiposAnaliseBalanco;
    }
}
