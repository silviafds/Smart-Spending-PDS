import React, { useEffect, useState } from 'react';
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";
import { Switch } from "../../componentes/switch/Switch";
import {useForm} from "react-hook-form";
import {validaDadosSubmissao} from "../../logica/Validacoes/CadastroDespesaValidacao";

interface IFormInputs {
    metaDespesa: number;
    metaReceita: number;
    tempoConselho: string;
}

function Conselhos() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [isCheckedDespesa, setCheckedDespesa] = useState(false);
    const [isCheckedReceita, setCheckedReceita] = useState(false);
    const [opcaoSelecionada, setOpcaoSelecionada] = useState(null);

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

    const handleCheckboxChange = (opcao: any) => {
        if (opcao === opcaoSelecionada) {
            setOpcaoSelecionada(null); // Desmarca a opção se ela já estiver selecionada
        } else {
            setOpcaoSelecionada(opcao); // Marca a opção selecionada
        }
    };

    const handleSalvar = () => {
        // Coloque aqui a lógica que você deseja executar quando o botão salvar for clicado
        console.log("Botão Salvar clicado!");
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Gerenciador de Conselhos </h1>
                        <Ajuda tipoAjuda={AjudaEnum.GERENCIADOR_CONSELHO}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="flex justify-center items-center w-full">
                        <div className="p-5 mt-4 sm:w-9/12 md:w-9/12 lg:w-5/12 border-solid border-1
                         border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl">

                            <div className={"flex justify-between"}>
                                <p className={"mr-4"}>Cadastrar meta despesa</p>
                                <Switch checked={isCheckedDespesa} onCheckedChange={setCheckedDespesa}/>
                            </div>

                            {isCheckedDespesa && true && (
                                <div className="inputs relative my-4">
                                    <input
                                        {...register('metaDespesa', {required: false})}
                                        placeholder={"Valor meta despesa"}
                                        type={"number"}
                                        value={watch('metaDespesa')}
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
                                        {...register('metaReceita', {required: false})}
                                        placeholder={"Valor meta receita"}
                                        type={"number"}
                                        value={watch('metaReceita')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                            )}

                            <div className="inputs relative mt-4 border rounded-md border-slate-200 p-2">
                                <p>Selecione uma unidade de tempo:</p>
                                <div className="flex items-center mt-3">
                                    <input type="checkbox" checked={opcaoSelecionada === '15d'}
                                           onChange={() => handleCheckboxChange('15d')}/>
                                    <p className="ml-4">15 dias</p>
                                </div>

                                <div className="flex items-center mt-3">
                                    <input type="checkbox" checked={opcaoSelecionada === '1m'}
                                           onChange={() => handleCheckboxChange('1m')}/>
                                    <p className="ml-4">Um mês</p>
                                </div>

                                <div className="flex items-center mt-3">
                                    <input type="checkbox" checked={opcaoSelecionada === '2m'}
                                           onChange={() => handleCheckboxChange('2m')}/>
                                    <p className="ml-4">Dois meses</p>
                                </div>
                            </div>

                            <div className="flex justify-center p-2 mt-4">
                                <button
                                    onClick={handleSalvar}
                                    className="bg-secundaria_esmeralda hover:bg-bota_acao_hover text-white font-bold py-2 px-4 rounded w-2/6"
                                >
                                    Salvar
                                </button>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Conselhos;