import axios from "../../../contexto/axios";
import { BACKEND_URL } from "../../../core/config";
import Swal from "sweetalert2";

export async function buscarContaBancaria() {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaBancaria/buscarContaBancaria`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de conta bancária', error);
        throw error;
    }
}

export async function buscarContaBancariaPorID(idContaBancaria: string | undefined) {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaBancaria/buscarContaBancariaPorId/${idContaBancaria}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de conta bancária', error);
        throw error;
    }
}

export async function buscarBancoPorNome() {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaBancaria/buscarContaBancariaPorNome`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os bancos', error);
        throw error;
    }
}

export async function buscarDadosBancariosPorBanco(banco: string) {
    try {
        const response = await axios.get(`${BACKEND_URL}/contaBancaria/buscarDadosBancariosPorBanco/${banco}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os bancos', error);
        throw error;
    }
}

export async function deletarContaBancaria(idContaBancaria: number) {
    try {
        const response = await axios.delete(`${BACKEND_URL}/contaBancaria/deletarContaBancaria/${idContaBancaria}`);
    } catch (error) {
        console.error('Erro ao deletar Conta Bancária', error);
        throw error;
    }
}

export async function salvarContaBancaria(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/contaBancaria/registrarContaBancaria", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Conta Bancária salva.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/contaBancaria/"
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
            title: "Sua Conta Bancária contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function editarContaBancaria(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/contaBancaria/editarContaBancaria", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Conta Bancária atualizada.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/contaBancaria/"
            })
            .catch(function (error) {
                if (error.response && error.response.status === 400) {
                    const responseData = error.response.data;
                    console.log('Erro de BadRequest:', responseData);
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
            title: "Sua Conta Bancária contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}