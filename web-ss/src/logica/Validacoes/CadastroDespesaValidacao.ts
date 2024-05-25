import { verificaContaInterna } from "../API/ContaInternaAPI";

export function validaDadosSubmissao(
    id: string,
    contaInterna: string,
    categoria: string,
    titulo_contabil: string,
    dataDespesa: Date,
    categoriaTransacao: string,
    bancoOrigem: string,
    dadosBancariosOrigem: string,
    beneficiario: string,
    bancoDestino: string,
    agenciaDestino: string,
    numeroContaDestino: string,
    descricao: string,
    identificadorProjeto: number,
    valorProjeto: string
): boolean {
    if (categoriaTransacao === "Pix" || categoriaTransacao === "Transferência") {
        if (
            contaInterna === null || contaInterna === undefined ||
            categoria === null || categoria === undefined ||
            titulo_contabil === null || titulo_contabil === undefined ||
            dataDespesa === null || dataDespesa === undefined ||
            bancoOrigem === null || bancoOrigem === undefined ||
            dadosBancariosOrigem === null || dadosBancariosOrigem === undefined ||
            beneficiario === null || beneficiario === undefined ||
            descricao === null || descricao === undefined
        ) {
            console.log("opa")
            return true;
        }
    } else if (categoriaTransacao === "Papel e moeda" || categoriaTransacao === "Cheque") {
        if (
            contaInterna === null || contaInterna === undefined ||
            categoria === null || categoria === undefined ||
            titulo_contabil === null || titulo_contabil === undefined ||
            dataDespesa === null || dataDespesa === undefined ||
            beneficiario === null || beneficiario === undefined ||
            descricao === null || descricao === undefined
        ) {
            console.log("eita")
            return true;
        }
    }
    console.log("teste 1: "+valorProjeto)
    const jsonData = {
        id: id || null,
        contaInterna: contaInterna,
        categoria: categoria,
        titulo_contabil: titulo_contabil,
        dataDespesa: dataDespesa,
        categoriaTransacao: categoriaTransacao,
        bancoOrigem: bancoOrigem,
        dadosBancariosOrigem: dadosBancariosOrigem,
        beneficiario: beneficiario,
        bancoDestino: bancoDestino,
        agenciaDestino: agenciaDestino,
        numeroContaDestino: numeroContaDestino,
        descricao: descricao,
        identificadorProjeto: identificadorProjeto,
        valorProjeto: valorProjeto
    };
    verificaContaInterna(jsonData, id ? "editarDespesa" : "salvarDespesa");
    return false;
}