export enum TipoBalanco {
    DESPESA = "Despesa",
    RECEITA = "Receita",
    DESPESA_RECEITA = "Despesa e Receita"
}

export enum TipoBalancoHospital {
    DESPESA = "Despesa"
}

export const balancoEnum = Object.values(TipoBalanco).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export const balancoHospitalEnum = Object.values(TipoBalancoHospital).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export enum AnaliseBalancoReceita {
    ORIGEM_MAIS_RENTAVEL = "Origens mais rentáveis",
    BUSCAR_TODAS_RECEITAS = "Listagem receita por tempo"
}

export enum AnaliseBalancoDespesa {
    MAIORES_MEIOS_PAGAMENTO = "Pagamentos mais utilizados",
    BUSCAR_TODAS_DESPESAS = "Listagem despesa por tempo"
}

export enum AnaliseBalancoDespesaReceita {
    BALANCO_GERAL = "Balanço de entradas e/ou saídas",
}

export enum AnaliseTipoDespesaReceita {
    CATEGORIA = "Categoria",
    TITULO_CONTABIL = "Titulo Contábil"
}

export enum AnaliseBalancoDespesaHospital {
    MAQUINARIO = "Manuntenção de maquinário",
    MANUTENCAO_LEITOS_UTI = "Manutenção leitos UTI",
    MANUTENCAO_MAQUINARIO = "Manutenção maquinário"
}

export enum AnaliseBalancoReceitaHospital {
    MAQUINARIO = "Maquinário comprado",
    MANUTENCAO_LEITOS_UTI = "Manutenção leitos UTI",
    MANUTENCAO_MAQUINARIO = "Manutenção maquinário"
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

export const analiseTipoBalancoReceitaDespesaEnum = Object.values(AnaliseTipoDespesaReceita).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export const analiseBalancoDespesaHospital = Object.values(AnaliseBalancoDespesaHospital).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));

export const analiseBalancoReceitaHospital = Object.values(AnaliseBalancoReceitaHospital).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));