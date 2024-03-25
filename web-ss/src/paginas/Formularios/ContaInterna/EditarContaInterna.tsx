import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../../components/header/headerPadrao";
import { Sidebar } from "../../../components/sidebar/sidebar";
import axios from '../../../contexto/axios';
import { BACKEND_URL } from "../../../core/config";
import { useForm } from "react-hook-form";
import { Ajuda } from "../../../components/ajuda/Ajuda";
import { AjudaEnum } from "../../../core/ENUM/Ajuda";
import Swal from "sweetalert2";
import { useParams } from 'react-router-dom';
import { ContaInternaDTO } from "../../../core/DTO/ContaInternaDTO";

function EditarContaInterna() {
    const [nome, setNome] = useState('');
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const { idParaEditar } = useParams<{ idParaEditar: string }>();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    const {
        register,
        formState: { errors },
        handleSubmit,
    } = useForm<{ nome: string }>();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${BACKEND_URL}/contaInterna/buscarContaInvidual/${idParaEditar}`);
                setNome(response.data.nome);
            } catch (error) {
                console.error('Erro ao carregar nome da conta:', error);
            }
        };
        fetchData();
    }, [idParaEditar]);

    const onSubmit = async (data: { nome: string }) => {
        try {
            const dto = new ContaInternaDTO(data.nome);
            dto.id = Number(idParaEditar);
            const payload = {
                id: dto.id,
                nome: dto.nome
            };

            const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
            await axios.patch(BACKEND_URL + "/contaInterna/editarConta", payload, axiosConfig)
                .then((response) => {
                    Swal.fire({
                        icon: "success",
                        title: "Conta interna atualizada.",
                        showConfirmButton: true,
                        confirmButtonColor: "#072e66",
                        confirmButtonText: "OK",
                        customClass: {
                            confirmButton: "bg-sky-950",
                        },
                    });
                    window.location.href = "/contaInterna/"
                    console.log("dado salvo: ", response.data);
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
            console.error('Erro ao editar conta interna:', error);
            Swal.fire({
                icon: "error",
                title: "Ocorreu um erro ao editar a conta interna.",
                showConfirmButton: true,
                confirmButtonColor: "#b00303",
                confirmButtonText: "OK",
                customClass: {
                    confirmButton: "bg-sky-950",
                },
            });
        }
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen h-20 p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Edição de Conta Interna </h1>
                        <Ajuda tipoAjuda={AjudaEnum.CADASTRO_CONTA_INTERNA}/>
                    </div>
                    <div className="flex justify-center items-center w-full  ">
                        <div className="p-5 mt-20 sm:w-80 md:w-96 lg:w-96 border-solid border-1 border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl 	">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                <div className="inputs relative my-4">
                                    <input
                                        {...register('nome', {required: true})}
                                        placeholder="Nome da conta"
                                        className="input-with-line w-full"
                                        value={nome}
                                        onChange={(e) => setNome(e.target.value)}
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.nome && <p>Insira o nome da conta.</p>}
                                <div className="p-2">
                                    <input
                                        type="submit"
                                        className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"
                                        value="Salvar Alterações"
                                    />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EditarContaInterna;