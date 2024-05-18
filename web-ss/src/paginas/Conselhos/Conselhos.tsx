import React, { useEffect, useState } from 'react';
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import { Switch } from "../../componentes/switch/Switch";
import { useForm } from "react-hook-form";
import { formatarMoeda } from "../../logica/formatador";
import { buscarConfiguracaoConselhos, salvarConselhos } from "../../logica/API/ConselhosAPI";
import Selector from "../../componentes/Selector";
import {tempoConselhoEnum} from "../../core/ENUM/Conselho";

interface IFormInputs {
    tempo_conselho: string;
    meta_receita: string;
    meta_despesa: string;
}

function Conselhos() {
    const [erro, setErro] = useState<boolean>(false);
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [isCheckedDespesa, setCheckedDespesa] = useState(false);
    const [isCheckedReceita, setCheckedReceita] = useState(false);

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [conselho] = await Promise.all([
                    buscarConfiguracaoConselhos()
                ]);

                for (let prop in conselho) {
                    setValue('meta_despesa', conselho[prop].meta_despesa)
                    setValue('meta_receita', conselho[prop].meta_receita);
                    setValue('tempo_conselho', conselho[prop].tempo_conselho)
                    setCheckedDespesa(conselho[prop].status_despesa)
                    setCheckedReceita(conselho[prop].status_receita)
                }

            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };
        fetchData();
    }, []);

    const handleChangeDespesa = (event: { target: { value: string | number; }; }) => {
        const formattedValue = formatarMoeda(event.target.value);
        setValue("meta_despesa", formattedValue)
    };

    const handleChangeReceita = (event: { target: { value: string | number; }; }) => {
        const formattedValue = formatarMoeda(event.target.value);
        setValue("meta_receita", formattedValue)
    };

    const handleTempoConselho = async (dado: any) => {
        setValue('tempo_conselho', dado.nome);
    }

    const onSubmit = (data: IFormInputs) => {
        let isValid = false;

        if (isCheckedReceita && data.meta_receita.length > 1) {
            isValid = true;
        } else {
            setErro(true);
        }

        if (isValid && isCheckedDespesa && data.meta_despesa.length > 1) {
            const conselhoData = {
                identificador: 1,
                statusDespesa: isCheckedDespesa,
                metaDespesa: data.meta_despesa,
                statusReceita: isCheckedReceita,
                metaReceita: data.meta_receita,
                tempoConselho: data.tempo_conselho
            };
            const dadosJson = JSON.stringify(conselhoData);
            salvarConselhos(dadosJson);
        } else {
            setErro(true);
        }
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <div className={"flex"}>
                <Sidebar />
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Gerenciador de Conselhos </h1>
                        <Ajuda tipoAjuda={AjudaEnum.GERENCIADOR_CONSELHO} />
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"} />

                    <div className="flex justify-center items-center w-full">
                        <div className="p-5 mt-4 sm:w-9/12 md:w-9/12 lg:w-5/12 border-solid border-1
                         border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                {erro && (
                                    <>
                                        <div className="inputs relative my-4 bg-red-300 p-2 rounded-md border-rose-800 border-2">
                                            <p className={"text-black font-medium"}>Prencha todos os campos.</p>
                                        </div>
                                    </>
                                )}

                                <div className={"flex justify-between"}>
                                    <p className={"mr-4"}>Cadastrar meta despesa</p>
                                    <Switch checked={isCheckedDespesa} onCheckedChange={setCheckedDespesa}/>
                                </div>

                                {isCheckedDespesa && true && (
                                    <div className="inputs relative my-4">
                                        <input
                                            {...register('meta_despesa', {required: false})}
                                            placeholder={"Valor meta despesa"}
                                            type={"text"}
                                            value={watch('meta_despesa')}
                                            onChange={handleChangeDespesa}
                                            className="input-with-line w-full"
                                        />
                                        <div className="line"></div>
                                    </div>
                                )}

                                <div className={"flex justify-between mt-6"}>
                                    <p className={"mr-4"}>Cadastrar meta receita</p>
                                    <Switch checked={isCheckedReceita} onCheckedChange={setCheckedReceita}/>
                                </div>

                                {isCheckedReceita && true && (
                                    <div className="inputs relative my-4">
                                        <input
                                            {...register('meta_receita', {required: false})}
                                            placeholder={"Valor meta receita"}
                                            type={"text"}
                                            value={watch('meta_receita')}
                                            onChange={handleChangeReceita}
                                            className="input-with-line w-full"
                                        />
                                        <div className="line"></div>
                                    </div>
                                )}

                                <p>Selecione uma unidade de tempo:</p>
                                <div className="inputs relative">
                                    <Selector dado={tempoConselhoEnum}
                                              placeholder={"Selecione uma unidade de tempo."}
                                              valorSelecionado={watch('tempo_conselho')}
                                              onGenericoSelect={handleTempoConselho}/>
                                    <div className="line"></div>
                                </div>

                                <div className="flex justify-center p-2 mt-4">
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

export default Conselhos;