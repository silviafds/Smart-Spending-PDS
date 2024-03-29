import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../../componentes/header/headerPadrao";
import {Sidebar} from "../../../componentes/sidebar/sidebar";
import {Ajuda} from "../../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import Selector from "../../../componentes/Selector";
import {contaBancaria} from "../../../core/ENUM/TipoContaBancaria";
import {Titulos} from "../../../core/ENUM/Titulos";
import {useForm} from "react-hook-form";
import {  buscarContaBancariaPorID, editarContaBancaria } from "../../../logica/API/ContaBancaria/ContaBancariaAPI";
import {useParams} from "react-router-dom";

interface IFormInputs {
    nomeBanco: string;
    tipoConta: string;
    agencia: string;
    numeroConta: number;
}

export function EditarContaBancaria() {
    const { idParaEditar } = useParams<{ idParaEditar: string }>();
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [nomeBanco, setNomeBanco] = useState<string>("");
    const [tipoConta, setTipoConta] = useState<string>("");
    const [agencia, setAgencia] = useState<string>("");
    const [numeroConta, setNumeroConta] = useState<string>("");
    const [tipoContaBancaria, setTipoContaBancaria] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<IFormInputs>();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [contaBancaria] = await Promise.all([
                    buscarContaBancariaPorID(idParaEditar)
                ]);
                for (let prop in contaBancaria) {
                    setNomeBanco(contaBancaria[prop].nomeBanco)
                    setTipoConta(contaBancaria[prop].tipoConta)
                    setAgencia(contaBancaria[prop].agencia)
                    setNumeroConta(contaBancaria[prop].numeroConta)
                }
            } catch (error) {
                console.error("Erro ao carregar os dados de Conta Bancária", error);
            }
        };

        fetchData();
    }, []);

    const onSubmit = async (data: IFormInputs) => {
        const jsonData = {
            id: idParaEditar,
            nomeBanco: nomeBanco,
            tipoConta: tipoContaBancaria,
            agencia: agencia,
            numeroConta: numeroConta
        };

        editarContaBancaria(jsonData);
    };

    const handleTipoContaBancariaSelect = async (tipoConta: any) => {
        if(tipoConta.nome != null) {
            setTipoContaBancaria(tipoConta.nome);
        }
    };

    return(
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Cadastro de Conta Bancária </h1>
                        <Ajuda tipoAjuda={AjudaEnum.CADASTRO_CONTA_BANCARIA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="flex justify-center items-center w-full">
                        <div
                            className="p-5 mt-4 sm:w-11/12 md:w-11/12 lg:w-11/12 border-solid border-1 border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                <div className="inputs relative my-4">
                                    <input
                                        {...register('nomeBanco', {required: true})}
                                        placeholder="Digite o nome do banco"
                                        type={"text"}
                                        value={nomeBanco}
                                        onChange={(e) => setNomeBanco(e.target.value)}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.nomeBanco && <p>Informe o nome do banco.</p>}

                                <div className="inputs relative my-4">
                                    <Selector dado={contaBancaria}
                                              placeholder={Titulos.INPUT_TIPO_CONTA.toString()}
                                              valorSelecionado={tipoConta} onGenericoSelect={handleTipoContaBancariaSelect}/>
                                    <div className="line"></div>
                                </div>
                                {errors.tipoConta && <p>Informe o tipo da conta.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('agencia', {required: true})}
                                        placeholder="Digite o número da agência"
                                        type={"text"}
                                        value={agencia}
                                        onChange={(e) => setAgencia(e.target.value)}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.agencia && <p>Informe o número da agência.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('numeroConta', {required: true})}
                                        placeholder="Digite o número da conta"
                                        type={"text"}
                                        value={numeroConta}
                                        onChange={(e) => setNumeroConta(e.target.value)}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.numeroConta && <p>Informe o número da conta.</p>}

                                <div className="flex justify-center p-2">
                                    <input
                                        type="submit"
                                        className="bg-secundaria_esmeralda hover:bg-bota_acao_hover text-white font-bold py-2 px-4 rounded w-6/12"
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

export default EditarContaBancaria;