import {HeaderPadrao} from "../../componentes/header/headerPadrao";
import {Sidebar} from "../../componentes/sidebar/sidebar";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useForm} from "react-hook-form";
import {Titulos} from "../../core/ENUM/Titulos";
import {validaDadosSubmissao} from "../../logica/Validacoes/CadastroProjetoValidacao";
import {buscarṔrojetoPorId} from "../../logica/API/ProjetosAPI";

interface IFormInputs {
    nome: string;
    valor_meta: number;
    data_inicio: Date;
    data_final: Date;
    descricao: string;
    valor_atual: number;
}

export function CadastroProjeto() {
    let { id } = useParams();
    const [fetchDataComplete, setFetchDataComplete] = useState(false);

    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [erro, setErro] = useState<boolean>(false);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuarioLocalStorage(storageUser);
        }

        setFetchDataComplete(true);
    }, []);

    useEffect(() => {
        if (fetchDataComplete) {
            const fetchData = async () => {
                try {
                    if (id != undefined) {
                        const [projeto] = await Promise.all([
                            buscarṔrojetoPorId(id)
                        ]);
                        for (let prop in projeto) {
                            setValue('nome', projeto[prop].nome)
                            setValue('valor_meta', projeto[prop].valor_meta);
                            setValue('data_inicio', projeto[prop].data_inicio);
                            setValue('data_final', projeto[prop].data_final);
                            setValue('descricao', projeto[prop].descricao);
                            setValue('valor_atual', projeto[prop].valor_arrecadado_atual)
                        }
                    }
                } catch (error) {
                    console.error('Erro ao carregar os dados', error);
                }
            };

            fetchData();
        }
    }, [fetchDataComplete]);

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    const onSubmit = async (data: IFormInputs) =>  {
        id = id || '';
        setErro(false);
        let validacaoResultado = validaDadosSubmissao(id, data.nome, data.valor_meta,
            data.data_inicio, data.data_final, data.descricao, data.valor_atual);
        console.log("teste")
        if(validacaoResultado) {
            console.error('Algum dos dados está nulo.');
            setErro(true);
            return;
        }
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuarioLocalStorage}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Cadastro de Projeto </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_CADASTRO_PROJETO}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="flex justify-center items-center w-full">
                        <div
                            className="p-5 mt-4 sm:w-11/12 md:w-11/12 lg:w-11/12 border-solid border-1 border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                {erro && (
                                    <>
                                        <div
                                            className="inputs relative my-4 bg-red-300 p-2 rounded-md border-rose-800 border-2">
                                            <p className={"text-black font-medium"}>Prencha todos os campos.</p>
                                        </div>
                                    </>
                                )}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('nome', {required: false})}
                                        placeholder={Titulos.NOME_PROJETO.toString()}
                                        type={"text"}
                                        value={watch('nome')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.nome && <p>Informe o nome do projeto.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('valor_meta', {required: false})}
                                        placeholder={Titulos.VALOR_META.toString()}
                                        type={"text"}
                                        value={watch('valor_meta')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>

                                    {errors.valor_meta && (<p>Insira o valor da meta.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <label className="text-gray-500">Data Inicio do Projeto</label>
                                    <input
                                        {...register('data_inicio', {required: false})}
                                        type="date"
                                        value={watch('data_inicio') ? new Date(watch('data_inicio')).toISOString().substr(0, 10) : ''}
                                        placeholder={Titulos.INPUT_DATA_INICIO_PROJETO.toString()}
                                        className="text-lg text-black input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                    {errors.data_inicio && (<p>Insira a data de início.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <label className="text-gray-500">Data Final do Projeto</label>
                                    <input
                                        {...register('data_final', {required: false})}
                                        type="date"
                                        value={watch('data_final') ? new Date(watch('data_final')).toISOString().substr(0, 10) : ''}
                                        placeholder={Titulos.INPUT_DATA_FIM_PROJETO.toString()}
                                        className="text-lg text-black input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                    {errors.data_final && (<p>Insira a data final.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <textarea
                                        {...register('descricao', {required: false})}
                                        placeholder={Titulos.INPUT_DESCRICAO_RECEITA.toString()}
                                        className="input-with-line w-full"
                                        rows={4}
                                        value={watch('descricao')}
                                    ></textarea>
                                    <div className="line"></div>
                                </div>

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
    )
}

export default CadastroProjeto;