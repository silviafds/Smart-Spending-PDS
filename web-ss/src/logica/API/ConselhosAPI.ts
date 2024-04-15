import axios from "../../core/contexto/axios";
import {BACKEND_URL} from "../../core/config";
import Swal from "sweetalert2";

export async function salvarConselhos(jsonString: any) {
    try {
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/conselhos/registroConselho", jsonString, axiosConfig)
            .then((response) => {
                Swal.fire({
                    icon: "success",
                    title: "Conselho salvo.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                window.location.href = "/dashboard/"
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
            title: "Seu conselho contém erros.",
            showConfirmButton: true,
            confirmButtonColor: "#D60000",
            confirmButtonText: "OK",
            customClass: {
                confirmButton: "bg-sky-950",
            },
        });
    }
}