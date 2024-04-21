export enum tempoConselho {
    QUINZE_MESES = "15 dias",
    UM_MES = "1 mês",
    DOIS_MESES = "2 meses"
}

export const tempoConselhoEnum = Object.values(tempoConselho).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));