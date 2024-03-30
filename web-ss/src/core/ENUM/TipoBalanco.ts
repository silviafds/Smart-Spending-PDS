export enum TipoBalanco {
    DESPESA = "Despesa",
    RECEITA = "Receita",
    DESPESA_RECEITA = "Despesa e Receita",
    PROJETOS = "Projetos"
}

export const balancoEnum = Object.values(TipoBalanco).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export enum AnaliseBalancoReceita {
    ORIGEM_MAIS_RENTAVEL = "Origens mais rentáveis",
}

export enum AnaliseBalancoDespesa {
    MAIORES_MEIOS_PAGAMENTO = "Pagamentos mais utilizados",
}

export enum AnaliseBalancoDespesaReceita {
    BALANCO_GERAL = "Balanço de entradas e/ou saídas",
}

export const analiseBalancoReceitaEnum = Object.values(AnaliseBalancoReceita).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export const analiseBalancoDespesaEnum = Object.values(AnaliseBalancoDespesa).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export const analiseBalancoReceitaDespesaEnum = Object.values(AnaliseBalancoDespesaReceita).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));