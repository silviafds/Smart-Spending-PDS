package com.smartSpd.smartSpding.Core.Enum;

public enum TiposBalanco {
    BUSCAR_TODAS_DESPESAS("Listagem despesa por tempo"),
    BUSCAR_TODAS_RECEITAS("Listagem receita por tempo"),
    DESPESA_RECEITA("Despesa e Receita"),
    PAGAMENTOS_MAIS_UTILIZADOS("Pagamentos mais utilizados"),
    ORIGENS_MAIS_RENTAVEIS("Origens mais rentáveis"),
    PROJETOS("Projetos"),
    //Hospital
    MANUTENCAO_MAQUINARIO("Manutenção maquinário"),
    MANUTENCAO_LEITOS_UTI("Manutenção leitos UTI"),
    MAQUINARIO_COMPRADO("Maquinário comprado"),
    //Restaurante
    TREINAMENTO_FUNCIONARIOS("Treinamento funcionários"),
    MARKETING_PROPAGANDA("Marketing e propaganda"),
    DECORACAO_AMBIENTE("Decoração de ambiente"),
    //Supermercado
    ENTREGA("Entrega"),
    RELACIONAMENTO_CLIENTES("Relacionamento com clientes"),
    SERVICOS_TERCEIRIZADOS("Serviços terceirizados");


    private String tiposBalanco;

    TiposBalanco(String tiposBalanco) {
        this.tiposBalanco = tiposBalanco;
    }

    public String getTiposBalanco() {
        return tiposBalanco;
    }

}