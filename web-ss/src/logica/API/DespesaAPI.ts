import axios from "../../core/contexto/axios";
import { BACKEND_URL } from "../../core/config";
import Swal from "sweetalert2";

export async function buscarCategoriasDespesa() {
    try {
        const response = await axios.get(`${BACKEND_URL}/despesa/buscarCategoriaDespesa`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de categoria', error);
        throw error;
    }
}

export async function buscarContaInternaDespesa() {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaInterna/buscarContaHabilitadas`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de conta interna', error);
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


export async function salvarDespesa(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/despesa/registrarDespesa", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Despesa salva.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "/despesa/";
                    }
                });
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
                    console.error("request: ", error.request);
                } else {
                    console.error('Error', error.message);
                }
                console.error(error.config);
            });
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Sua despesa contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function buscarDespesaPorId(idParaEditar: any) {
    try {
        const response = await axios.get(`${BACKEND_URL}/despesa/buscarDespesasPorId/${idParaEditar}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de despesa', error);
        throw error;
    }
}

export async function buscarTitulosContabeisDespesa(categoriaId: string) {
    try {
        let identificador = Number(categoriaId);
        const response = await axios.get(`${BACKEND_URL}/despesa/buscarTituloContabilDespesa/${identificador}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os títulos contábeis', error);
        throw error;
    }
}

export async function buscarTodasDespesas() {
    try {
        const response = await axios.get(`${BACKEND_URL}/despesa/buscarDespesa`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de despesa', error);
        throw error;
    }
}


export async function editarDespesa(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/despesa/editarDespesa", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Despesa atualizada.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/despesa/"
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
                    console.error("request: ", error.request);
                } else {
                    console.error('Error', error.message);
                }
                console.error(error.config);
            });
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Sua despesa contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}