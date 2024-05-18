import axios from "../../core/contexto/axios";
import {BACKEND_URL} from "../../core/config";
import { ContaInterna } from "../../core/Dominio/ContaInterna";
import {editarReceita, salvarReceita} from "./ReceitaAPI";
import {editarDespesa, salvarDespesa} from "./DespesaAPI";

export async function buscarTodasContaInterna() {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaInterna/buscarConta`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar as contas internas', error);
        throw error;
    }
}

export async function verificaContaInterna(DadosJsonReceita: any, tipo: string) {
    try {
        const contasInternas = await buscarTodasContaInterna();
        const contaEncontrada = contasInternas.find((conta: any) => conta.nome === DadosJsonReceita.contaInterna);

        if (contaEncontrada) {
            const contaInternaEncontrada = new ContaInterna(
                contaEncontrada.id,
                contaEncontrada.nome,
                contaEncontrada.desabilitarConta
            );
            DadosJsonReceita.contaInterna = contaInternaEncontrada;
            switch (tipo) {
                case "salvarReceita":
                    await salvarReceita(DadosJsonReceita);
                    break;
                case "salvarDespesa":
                    await salvarDespesa(DadosJsonReceita);
                    break;
                case "editarReceita":
                    await editarReceita(DadosJsonReceita);
                    break;
                case "editarDespesa":
                    await editarDespesa(DadosJsonReceita);
                    break;
                default:
                    console.error('Tipo de ação inválido:', tipo);
                    break;
            }
        }
    } catch (error) {
        console.error('Erro ao verificar a conta interna', error);
        throw error;
    }
}