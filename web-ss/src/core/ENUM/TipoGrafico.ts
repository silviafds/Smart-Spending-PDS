export enum TipoGrafico {
    GRAFICO_COLUNAS = "Gráfico em Colunas",
    GRAFICO_PIZZA = "Gráfico de Pizza",
    GRAFICO_DONUT = "Gráfico de Donut",
}

export const graficoEnum = Object.values(TipoGrafico).map((tipo, index) => ({
    id: index + 1,
    nome: tipo
}));