import {verificaContaInterna} from "../API/ContaInternaAPI";

export function validaDadosSubmissao(id: string, contaInterna: string, categoria: string, titulo_contabil: string, dataReceita: Date,
                                     valorReceita: number, pagador: string, origem: string, bancoOrigem: string,
                                     agenciaOrigem: string, numeroContaOrigem: string, bancoDestino: string,
                                     dadosBancariosDestino: string, descricao: string, valorProjeto: string
): boolean {
    if (origem === "Pix" || origem === "Transferência") {
        if (
            contaInterna === null || contaInterna === undefined ||
            categoria === null || categoria === undefined ||
            titulo_contabil === null || titulo_contabil === undefined ||
            dataReceita === null || dataReceita === undefined ||
            /*valorReceita === null || valorReceita === undefined ||*/
            bancoOrigem === undefined ||
            pagador === null || pagador === undefined ||
            dadosBancariosDestino === null || dadosBancariosDestino === undefined ||
            descricao === null || descricao === undefined
        ) {
            return true;
        }
    } else if (origem === "Papel e moeda" || origem === "Cheque") {
        if (
            contaInterna === null || contaInterna === undefined ||
            categoria === null || categoria === undefined ||
            titulo_contabil === null || titulo_contabil === undefined ||
            dataReceita === null || dataReceita === undefined ||
            /*valorReceita === null || valorReceita === undefined ||*/
            pagador === null || pagador === undefined ||
            descricao === null || descricao === undefined
        ) {
            return true;
        }
    }

    const jsonData = {
        id: id || null,
        contaInterna: contaInterna,
        categoria: categoria,
        titulo_contabil: titulo_contabil,
        dataReceita: dataReceita,
        valorReceita: valorReceita,
        pagador: pagador,
        origem: origem,
        bancoOrigem: bancoOrigem,
        agenciaOrigem: agenciaOrigem,
        numeroContaOrigem: numeroContaOrigem,
        bancoDestino: bancoDestino,
        dadosBancariosDestino: dadosBancariosDestino,
        descricao: descricao,
        valorProjeto: valorProjeto
    };
    verificaContaInterna(jsonData, id ? "editarReceita" : "salvarReceita");
    return false;
}