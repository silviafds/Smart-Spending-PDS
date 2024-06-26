import axios from "../../core/contexto/axios";
import {BACKEND_URL} from "../../core/config";
import Swal from "sweetalert2";

export async function buscarBalanco() {
    try {
        const response = await axios.get(`${BACKEND_URL}/balancoDespesa/buscarBalancos`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de Balanço', error);
        throw error;
    }
}

export async function buscarBalancoHospital() {
    try {
        const response = await axios.get(`${BACKEND_URL}/balancoDespesa/buscarBalancosHospital`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de Balanço', error);
        throw error;
    }
}

export async function buscarBalancoSupermercado() {
    try {
        const response = await axios.get(`${BACKEND_URL}/balancoDespesa/buscarBalancosSupermercado`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de Balanço', error);
        throw error;
    }
}

export async function buscarBalancoRestaurante() {
    try {
        const response = await axios.get(`${BACKEND_URL}/balancoDespesa/buscarBalancosRestaurante`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de Balanço', error);
        throw error;
    }
}


export async function cadastrarProjeto(jsonString: any, onClose: () => void) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/balancoDespesa/registrarBalanco", jsonString, axiosConfig)
            .then((response) => {
                onClose();
                Swal.fire({
                    icon: "success",
                    title: "Balanço salvo.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "/balanco/";
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
            title: "Seu balanço contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function buscarBalancoPorId(idParaEditar: any) {
    try {
        const response = await axios.get(`${BACKEND_URL}/balancoDespesa/buscarBalancoPorId/${idParaEditar}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de projeto', error);
        throw error;
    }
}

export async function editarBalanco(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/balancoDespesa/editarBalanco", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Balanço atualizado.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/balanco/"
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
            title: "Sua balanço contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function deletarBalancoPorId(idParaDeletar: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.delete(`${BACKEND_URL}/balancoDespesa/deletarBalanco/${idParaDeletar}`, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Balanço deletado.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "/balanco/";
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
            title: "Erro ao deletar balanço.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function editarBalancoDashboard(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/balancoDespesa/editarBalancoDashboard", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Balanço atualizado.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/balanco/"
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
            title: "Sua balanço contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

