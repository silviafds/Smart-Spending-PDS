import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../../components/header/headerPadrao";
import {Sidebar} from "../../../components/sidebar/sidebar";
import {Ajuda} from "../../../components/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import Selector from "../../../components/Selector";
import {contaBancaria} from "../../../core/ENUM/TipoContaBancaria";
import {Titulos} from "../../../core/ENUM/Titulos";
import {useForm} from "react-hook-form";
import {salvarContaBancaria} from "../../../logica/API/ContaBancaria/ContaBancariaAPI";

interface IFormInputs {
    nomeBanco: string;
    tipoConta: string;
    agencia: string;
    numeroConta: number;
}

export function CadastroContaBancaria() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
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

    const onSubmit = async (data: IFormInputs) => {
        const jsonData = {
            id: null,
            nomeBanco: data.nomeBanco,
            tipoConta: tipoContaBancaria,
            agencia: data.agencia,
            numeroConta: data.numeroConta
        };
        salvarContaBancaria(jsonData);
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
                                        {...register('nomeBanco', {required: false})}
                                        placeholder="Digite o nome do banco"
                                        type={"text"}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.nomeBanco && <p>Informe o nome do banco.</p>}

                                <div className="inputs relative my-4">
                                    <Selector dado={contaBancaria}
                                              placeholder={Titulos.INPUT_TIPO_CONTA.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleTipoContaBancariaSelect}/>
                                    <div className="line"></div>
                                </div>
                                {errors.tipoConta && <p>Informe o tipo da conta.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('agencia', {required: false})}
                                        placeholder="Digite o número da agência"
                                        type={"text"}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.agencia && <p>Informe o número da agência.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('numeroConta', {required: false})}
                                        placeholder="Digite o número da conta"
                                        type={"text"}
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

export default CadastroContaBancaria;