import axios from "../../core/contexto/axios";
import {BACKEND_URL} from "../../core/config";

export async function buscarProjetos() {
    try {
        const response = await axios.get(`${BACKEND_URL}/projetos/buscarProjetos`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de projetos', error);
        throw error;
    }
}