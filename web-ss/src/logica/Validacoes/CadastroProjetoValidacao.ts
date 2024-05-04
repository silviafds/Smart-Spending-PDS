import {cadastrarProjeto, editarProjeto} from "../API/ProjetosAPI";

export function validaDadosSubmissao(
    id: string,
    nome: string,
    valorMeta: number,
    dataInicio: Date,
    dataFinal: Date,
    descricao: string,
    valor_atual: number
): boolean {
    if (
        nome === null || nome === undefined ||
        dataInicio === null || dataInicio === undefined ||
        dataFinal === null || dataFinal === undefined ||
        valorMeta === null || valorMeta === undefined ||
        descricao === null || descricao === undefined
    ) {
        return true;
    }
    let valorAtual = 0;
    if(id != null) {
        valorAtual = valor_atual;
    }

    const jsonData = {
        id: id || null,
        nome: nome,
        categoria: "Projetos",
        valor_meta: valorMeta,
        data_inicio: dataInicio,
        data_final: dataFinal,
        descricao: descricao,
        valor_arrecadado_atual: valorAtual
    };

    if (id == '') {
        cadastrarProjeto(jsonData)
    } else {
        editarProjeto(jsonData)
    }
    return false;
}