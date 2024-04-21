export enum CalculoTempoConselhos {
    QUINZE_DIAS = "15 dias",
    UM_MES = "Um mês",
    DOIS_MESES = "Dois meses",
    TRES_MESES = "Três meses",
}

export const CalculoTempoConselhosEnum = Object.values(CalculoTempoConselhos).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));