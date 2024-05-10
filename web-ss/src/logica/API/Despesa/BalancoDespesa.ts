import axios from "../../../core/contexto/axios";
import { BACKEND_URL } from "../../../core/config";
import Swal from "sweetalert2";

export async function criarBalancoRapidoDespesa(jsonString: any) {
    try {
        console.log("metodo da requisição: "+jsonString.tipoBalanco)
        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
        await axios.post(BACKEND_URL + "/balanco/registroBalancoRapido", jsonString, axiosConfig)
            .then((response) => {

                window.location.href = "/BalancoRapido?dados=" + encodeURIComponent(JSON.stringify(response.data));

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
