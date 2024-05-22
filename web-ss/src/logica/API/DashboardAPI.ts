import axios from "../../core/contexto/axios";
import { BACKEND_URL } from "../../core/config";
import Swal from "sweetalert2";

export async function criarDashboard(jsonString: any, onClose: () => void) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/dashboard/criarDashboard", jsonString, axiosConfig)
            .then((response) => {
                onClose();
                Swal.fire({
                    icon: "success",
                    title: "Dashboard criado com sucesso.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
            })
            .catch(function (error) {
                handleError(error, "Erro ao criar dashboard.");
            });
    } catch (error) {
        showErrorAlert("Erro ao criar dashboard.");
    }
}

export async function buscarDashboard() {
    try {
        const response = await axios.get(`${BACKEND_URL}/dashboard/buscarDashboard`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados do Dashboard', error);
        throw error;
    }
}

export async function deletarDashboard(onClose: () => void) {
    try {
        await axios.delete(`${BACKEND_URL}/dashboard/deletarDashboard`)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Dashboard deletado com sucesso.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                }).then((result) => {
                    if (result.isConfirmed) {
                        onClose();
                    }
                });
            })
            .catch(function (error) {
                handleError(error, "Erro ao deletar dashboard.");
            });
    } catch (error) {
        showErrorAlert("Erro ao deletar dashboard.");
    }
}

export async function adicionarBalancoAoDashboard(jsonString: any, onClose: () => void) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/dashboard/adicionarBalanco", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Balanço adicionado ao dashboard com sucesso.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
            })
            .catch(function (error) {
                handleError(error, "Erro ao adicionar balanço ao dashboard.");
            });
    } catch (error) {
        showErrorAlert("Erro ao adicionar balanço ao dashboard.");
    }
}

export async function removerBalancoDoDashboard(jsonString: any, onClose: () => void) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.delete(BACKEND_URL + "/dashboard/removerBalanco", { data: jsonString, ...axiosConfig })
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Balanço removido do dashboard com sucesso.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
            })
            .catch(function (error) {
                handleError(error, "Erro ao remover balanço do dashboard.");
            });
    } catch (error) {
        showErrorAlert("Erro ao remover balanço do dashboard.");
    }
}

function handleError(error: any, defaultMessage: string) {
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
}


function showErrorAlert(message: string) {
    Swal.fire({
        icon: "error",
        title: message,
        showConfirmButton: true,
        confirmButtonColor: "#D60000",
        confirmButtonText: "OK",
        customClass: {
            confirmButton: "bg-sky-950",
        },
    });
}

