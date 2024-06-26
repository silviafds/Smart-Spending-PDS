import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import axios from '../../core/contexto/axios';
import { BACKEND_URL } from "../../core/config";
import { useForm } from "react-hook-form";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import {ContaInternaDTO} from "../../core/DTO/ContaInternaDTO";
import Swal from "sweetalert2";

interface IFormInputs {
    nome: string;
}

function formataParaJson(dadoUsuario: ContaInternaDTO) {
    let dados = {
        nome: dadoUsuario.nome,
        desabilitarConta: false,
    };
    return JSON.stringify(dados);
}

function CadastroContaInterna() {
    const [nome, setNome] = useState('');
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    const {
        register,
        formState: { errors },
        handleSubmit,
    } = useForm<IFormInputs>();

    const onSubmit = async (data: IFormInputs) => {
        try {
            const token = localStorage.getItem('@Auth:token');
            const dadoUsuario = new ContaInternaDTO(data.nome);
            const JsonData = formataParaJson(dadoUsuario);
            const response = await axios.post(`${BACKEND_URL}/contaInterna/registrarConta`, JsonData, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });
            await Swal.fire({
                icon: "success",
                title: "Conta Interna criada.",
                showConfirmButton: true,
                confirmButtonColor: "#029d02",
                confirmButtonText: "OK",
                customClass: {
                    confirmButton: "bg-sky-950",
                },
            });
            window.location.href = "/contaInterna/"
            console.log("dado salvo: ", response);
        } catch (error) {

            await Swal.fire({
                icon: "error",
                title: "Conta Interna já existe.",
                text: "Crie uma conta com nome diferente.",
                showConfirmButton: true,
                confirmButtonColor: "#D60000",
                confirmButtonText: "OK",
                customClass: {
                    confirmButton: "bg-sky-950",
                },
            });
        }
    };

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen h-20 p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Cadastro de Conta Interna </h1>
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
                                        onChange={(e) => setNome(e.target.value)}
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.nome && <p>Insira o nome da conta.</p>}
                                <div className="p-2">
                                    <input
                                        type="submit"
                                        className="bg-secundaria_esmeralda hover:bg-bota_acao_hover text-white font-bold py-2 px-4 rounded w-full"
                                        value="Cadastrar"
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

export default CadastroContaInterna;
