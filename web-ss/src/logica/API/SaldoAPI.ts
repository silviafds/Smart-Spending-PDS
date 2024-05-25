import axios from "../../core/contexto/axios";
import { BACKEND_URL } from "../../core/config";

export async function buscarSaldo() {
    try {
        const response = await axios.get(`${BACKEND_URL}/saldo/buscarSaldos`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de Saldo', error);
        throw error;
    }
}