export enum TipoContaBancaria {
    CORRENTE = "Corrente",
    POUPANCA = "Poupança",
    SALARIO = "Salário",
    INVESTIMENTO = "Investimento"
}

export const contaBancaria = Object.values(TipoContaBancaria).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));