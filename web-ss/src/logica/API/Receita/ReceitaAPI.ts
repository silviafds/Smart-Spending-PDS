import axios from "../../../core/contexto/axios";
import { BACKEND_URL } from "../../../core/config";
import Swal from "sweetalert2";

export async function buscarContaInternaReceita() {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaInterna/buscarContaHabilitadas`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de conta interna', error);
        throw error;
    }
}

export async function buscarCategoriasReceita() {
    try {
        const response = await axios.get(`${BACKEND_URL}/receita/buscarCategoriaReceita`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de categoria', error);
        throw error;
    }
}

export async function buscarTitulosContabeis(categoriaId: string) {
    try {
        let identificador = Number(categoriaId);
        var id = Number(categoriaId)
        const response = await axios.get(`${BACKEND_URL}/receita/buscarTituloContabilReceita/${identificador}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os títulos contábeis', error);
        throw error;
    }
}

export async function buscarOrigem() {
    try {
        const response = await axios.get(`${BACKEND_URL}/origem/buscarOrigem`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de origem', error);
        throw error;
    }
}

export async function buscarTodasReceitas() {
    try {
        const response = await axios.get(`${BACKEND_URL}/receita/buscarReceitas`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de receita', error);
        throw error;
    }
}

export async function buscarReceitaPorId(idParaEditar: any) {
    try {
        const response = await axios.get(`${BACKEND_URL}/receita/buscarReceitasPorId/${idParaEditar}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de receita', error);
        throw error;
    }
}

export async function editarReceita(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/receita/editarReceita", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Receita atualizada.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/receita/"
            })
            .catch(function (error) {
                if (error.response && error.response.status === 400) {
                    const responseData = error.response.data;
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: responseData.message,
                        customClass: {
                            confirmButton: 'error-button'
                        }
                    });
                } else if (error.request) {
                    console.log("request: ", error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            });
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Sua receita contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function salvarReceita(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/receita/registrarReceita", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Receita salva.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/receita/"
            })
            .catch(function (error) {
                if (error.response && error.response.status === 400) {
                    const responseData = error.response.data;
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: responseData.message,
                        customClass: {
                            confirmButton: 'error-button'
                        }
                    });
                } else if (error.request) {
                    console.log("request: ", error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            });
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Sua receita contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function chamada(id: any) {
    try {
        const titulos = await buscarTitulosContabeis(id);
        return titulos.data;
        /*setArrayTituloContabil(titulos);*/
    } catch (error) {
        console.error('Erro ao buscar os títulos contábeis', error);
    }
}

