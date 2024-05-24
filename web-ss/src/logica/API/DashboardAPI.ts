import axios from "../../core/contexto/axios";
import { BACKEND_URL } from "../../core/config";
import Swal from "sweetalert2";

export async function listaBalancoDash() {
    try {
        const response = await axios.get(`${BACKEND_URL}/dashboard/listaBalancoDash`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados do Dashboard', error);
        throw error;
    }
}

export async function buscarDashboard() {
    try {
        const response = await axios.get(`${BACKEND_URL}/dashboard/buscarBalancosDashboardProcessados`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados do Dashboard', error);
        throw error;
    }
}

export async function adicionarBalancoAoDashboard(jsonString: any,) {
    try {
        console.log("salvnado balanço no dashboard")
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/dashboard/adicionarBalancoDashboard", jsonString, axiosConfig)
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

export async function removerBalancoDoDashboard(jsonString: any) {
    try {
        console.log("removendo balanço no dashboard")
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.delete(BACKEND_URL + "/dashboard/deletarBalancoDashboard", { data: jsonString, ...axiosConfig })
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

