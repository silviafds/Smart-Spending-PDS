import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import { useForm } from "react-hook-form";
import { buscarContaInternaReceita, buscarCategoriasReceita, buscarTitulosContabeis,
    buscarOrigem, buscarReceitaPorId } from "../../logica/API/Receita/ReceitaAPI";
import Selector from "../../componentes/Selector";
import {Titulos} from "../../core/ENUM/Titulos";
import {buscarBancoPorNome, buscarDadosBancariosPorBanco} from "../../logica/API/ContaBancaria/ContaBancariaAPI";
import {useParams} from "react-router-dom";
import {validaDadosSubmissao} from "../../logica/Validacoes/CadastroReceitaValidacao";

interface IFormInputs {
    contaInterna: string;
    categoria: string;
    tituloContabil: string;
    valorReceita: number;
    dataReceita: Date;
    pagador: string;
    origem: string;
    bancoOrigem: string;
    agenciaOrigem: string;
    contaOrigem: string;
    bancoDestino: string;
    agenciaDestino: string;
    contaDestino: string;
    dadosBancarios: string;
    descricao: string;
    erro: boolean;
}

export function CadastroReceita() {

    let { id } = useParams();
    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [fetchDataComplete, setFetchDataComplete] = useState(false);
    const [configOrigem, setConfigOrigem] = useState<string>("");

    const [arrayContaInterna, setArrayContaInterna] = useState([]);
    const [arrayCategoria, setArrayCategoria] = useState([]);
    const [arrayTituloContabil, setArrayTituloContabil] = useState([]);
    const [arrayOrigem, setArrayOrigem] = useState([]);
    const [arrayBancos, setArrayBancos] = useState([]);
    const [arrayDadosBancarios, setArrayDadosBancarios] = useState([]);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuarioLocalStorage(storageUser);
        }
    }, []);

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [categorias, contasInternas, origens] = await Promise.all([
                    buscarCategoriasReceita(),
                    buscarContaInternaReceita(),
                    buscarOrigem()
                ]);

                if (id !== null) {
                    const [arrayBancos] = await Promise.all([
                        buscarBancoPorNome()
                    ]);
                    setArrayBancos(arrayBancos);
                }

                setArrayCategoria(categorias);
                setArrayContaInterna(contasInternas);
                setArrayOrigem(origens);
                setFetchDataComplete(true);

            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    useEffect(() => {
        if (fetchDataComplete) {
            const fetchData = async () => {
                var verificaCategoria;
                var verificaOrigem;
                var verificaDadosBancarios;
                try {
                    if (id !== undefined) {
                        const [receita] = await Promise.all([
                            buscarReceitaPorId(id)
                        ]);
                        for (let prop in receita) {
                            setValue('contaInterna', receita[prop].contaInterna.nome)
                            setValue('categoria', receita[prop].categoria);
                            setValue('tituloContabil', receita[prop].titulo_contabil);
                            setValue('valorReceita', receita[prop].valorReceita);
                            setValue('dataReceita', receita[prop].dataReceita);
                            setValue('pagador', receita[prop].pagador);
                            setValue('origem', receita[prop].origem);
                            setValue('bancoOrigem', receita[prop].bancoOrigem);
                            setValue('agenciaOrigem', receita[prop].agenciaOrigem);
                            setValue('contaOrigem', receita[prop].numeroContaOrigem)
                            setValue('bancoDestino', receita[prop].bancoDestino);
                            setValue('agenciaDestino', receita[prop].agenciaDestino);
                            setValue('contaDestino', receita[prop].contaDestino);
                            setValue('dadosBancarios', receita[prop].dadosBancarios);
                            setValue('descricao', receita[prop].descricao);

                            verificaCategoria = receita[prop].categoria;
                            verificaOrigem = receita[prop].origem
                            verificaDadosBancarios = receita[prop].bancoDestino
                        }
                        verificaDadoBancario(verificaDadosBancarios)
                        verificacaoCategorias(verificaCategoria);

                        const bancosCadastrados = await buscarBancoPorNome();
                        setArrayBancos(bancosCadastrados)

                        if(verificaOrigem === "Transferência" ) {
                            setConfigOrigem("Transferência")
                        } else if(verificaOrigem === "Pix") {
                            setConfigOrigem("Pix")
                        } else {
                            setConfigOrigem("false")
                        }
                    }
                } catch (error) {
                    console.error('Erro ao carregar os dados', error);
                }
            };

            fetchData();
        }
    }, [fetchDataComplete]);


    function verificaDadoBancario(verificaDadosBancarios: string) {
        let idReceita;
        arrayBancos.forEach(bancos => {
            // @ts-ignore
            if(bancos.nome === verificaDadosBancarios) {
                // @ts-ignore
                idReceita = bancos.nome;
            }
        });
        buscaDadosBancarios(idReceita);
    }
    const buscaDadosBancarios = async (id: any) => {
        try {
            const titulos = await buscarDadosBancariosPorBanco(id);
            setArrayDadosBancarios(titulos);
        } catch (error) {
            console.error('Erro ao buscar os dados bancários', error);
        }
    }
    function verificacaoCategorias(verificaCategoria: string) {
        let idReceita = 0;
        arrayCategoria.forEach(categoria => {
            // @ts-ignore
            if(categoria.nome === verificaCategoria) {
                // @ts-ignore
                idReceita = categoria.id;
            }
        });

        buscaTitulosContabeis(idReceita);
    }

    const buscaTitulosContabeis = async (id: any) => {
        try {
            const titulos = await buscarTitulosContabeis(id);
            setArrayTituloContabil(titulos);
        } catch (error) {
            console.error('Erro ao buscar os títulos contábeis', error);
        }
    };

    const handleContaInterna = async (dado: any) => {
        try {
            setValue('contaInterna', dado.nome);
        } catch (error) {
            console.error('Erro ao salvar as Contas Internas', error)
        }
    };
    const handleCategoria= async (dado: any) => {
        try {
            const titulosContabeis = await buscarTitulosContabeis(dado.id);
            setArrayTituloContabil(titulosContabeis);
            setValue('categoria', dado.nome);
        } catch (error) {
            console.error('Erro ao buscar os títulos contábeis', error);
        }
    };
    const handleTituloContabil = async (dado: any) => {
        try {
            setValue('tituloContabil', dado.nome);
        } catch (error) {
            console.error('Erro ao salvar titulo contabil', error)
        }
    };
    const handleOrigem = async (dado: any) => {
        const bancosCadastrados = await buscarBancoPorNome();
        setArrayBancos(bancosCadastrados)
        setValue('origem', dado.nome);
        if(dado.nome.toString() === "Transferência" ) {
            setConfigOrigem("Transferência")
        } else if(dado.nome.toString() === "Pix") {
            setConfigOrigem("Pix")
        } else {
            setConfigOrigem("false")
        }
    };
    const handleBancoDestino = async (dado: any) => {
        setValue('bancoDestino', dado.nome);
        const dadosBancariosRecebidos = await buscarDadosBancariosPorBanco(dado.nome)
        setArrayDadosBancarios(dadosBancariosRecebidos);
    };
    const handleGenericoDadosBancarios = async (dado: any) => {
        setValue('dadosBancarios', dado.nome);
    }

    const onSubmit = async (data: IFormInputs) => {
        id = id || '';
        setValue('erro', false);
        let validacaoResultado = validaDadosSubmissao(
            id,
            data.contaInterna,
            data.categoria,
            data.tituloContabil,
            data.dataReceita,
            data.valorReceita,
            data.pagador,
            data.origem,
            data.bancoOrigem,
            data.agenciaOrigem,
            data.contaOrigem,
            data.bancoDestino,
            data.dadosBancarios,
            data.descricao
        )
        if (validacaoResultado) {
            console.error('Algum dos dados está nulo.');
            setValue('erro', true);
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
                        <h1 className={"text-2xl font-semibold"}> Cadastro de Receita </h1>
                        <Ajuda tipoAjuda={AjudaEnum.CADASTRO_RECEITA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="flex justify-center items-center w-full">
                        <div
                            className="p-5 mt-4 sm:w-11/12 md:w-11/12 lg:w-11/12 border-solid border-1 border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl 	">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                {watch('erro') === true  && (
                                    <>
                                        <div className="inputs relative my-4 bg-red-300 p-2 rounded-md border-rose-800 border-2">
                                            <p className={"text-black font-medium"}>Prencha todos os campos.</p>
                                        </div>
                                    </>
                                )}

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayContaInterna}
                                              placeholder={Titulos.INPUT_CONTA_INTERNA.toString()}
                                              valorSelecionado={watch('contaInterna')}
                                              onGenericoSelect={handleContaInterna}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayCategoria}
                                              placeholder={Titulos.INPUT_CATEGORIA.toString()}
                                              valorSelecionado={watch('categoria')}
                                              onGenericoSelect={handleCategoria}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayTituloContabil}
                                              placeholder={Titulos.INPUT_TITULO_CONTABIL.toString()}
                                              valorSelecionado={watch('tituloContabil')}
                                              onGenericoSelect={handleTituloContabil}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('valorReceita', {required: false})}
                                        placeholder={Titulos.INPUT_VALOR_RECEITA.toString()}
                                        type={"number"}
                                        value={watch('valorReceita')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.valorReceita && <p>Selecione o título contábil.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('dataReceita', {required: false})}
                                        type="date"
                                        value={watch('dataReceita') ? new Date(watch('dataReceita')).toISOString().substr(0, 10) : ''}
                                        placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                        className="text-lg text-black input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                    {errors.dataReceita && (<p>Insira a data da receita.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('pagador', {required: false})}
                                        placeholder={Titulos.INPUT_PAGADOR.toString()}
                                        type={"text"}
                                        value={watch('pagador')}
                                        className="input-with-line w-full"/>
                                    <div className="line"></div>
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayOrigem}
                                              placeholder={Titulos.INPUT_ORIGEM.toString()}
                                              valorSelecionado={watch('origem')}
                                              onGenericoSelect={handleOrigem}/>
                                </div>

                                {configOrigem === "Transferência" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_ORIGEM.toString()}
                                                type={"text"}
                                                value={watch('bancoOrigem')}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('agenciaOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_AGENCIA_ORIGEM.toString()}
                                                type={"text"}
                                                value={watch('agenciaOrigem')}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('contaOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_CONTA_ORIGEM.toString()}
                                                type={"text"}
                                                value={watch('contaOrigem')}
                                                className="input-with-line w-full"
                                            />
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={watch('bancoDestino')}
                                                      onGenericoSelect={handleBancoDestino}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayDadosBancarios}
                                                      placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                      valorSelecionado={watch('dadosBancarios')}
                                                      onGenericoSelect={handleGenericoDadosBancarios}/>
                                        </div>
                                    </>
                                )}

                                {configOrigem === "Pix" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_ORIGEM.toString()}
                                                type={"text"}
                                                value={watch('bancoOrigem')}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={watch('bancoDestino')}
                                                      onGenericoSelect={handleBancoDestino}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayDadosBancarios}
                                                      placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                      valorSelecionado={watch('dadosBancarios')}
                                                      onGenericoSelect={handleGenericoDadosBancarios}/>
                                        </div>
                                    </>
                                )}

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
    );
}

export default CadastroReceita;