import axios from "../../core/contexto/axios";
import {BACKEND_URL} from "../../core/config";
import Swal from "sweetalert2";

export async function buscarProjetos() {
    try {
        const response = await axios.get(`${BACKEND_URL}/projetos/buscarProjetos`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de projetos', error);
        throw error;
    }
}

export async function cadastrarProjeto(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/projetos/registroProjetos", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Projeto salvo.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = "/projetos/";
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
                    console.log("request: ", error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            });
    } catch (error) {
        await Swal.fire({
            icon: "error",
            title: "Seu projeto contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}

export async function buscarṔrojetoPorId(idParaEditar: any) {
    try {
        const response = await axios.get(`${BACKEND_URL}/projetos/buscarProjetoInvidual/${idParaEditar}`);
        return response.data;
    } catch (error) {
        console.error('Erro ao carregar os dados de projeto', error);
        throw error;
    }
}

export async function editarProjeto(jsonString: any) {
    try {
        console.log("tentando editar projeto")
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.patch(BACKEND_URL + "/projetos/editarProjeto", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Projeto atualizado.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/projetos/"
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