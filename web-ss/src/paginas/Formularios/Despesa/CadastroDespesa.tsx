import {HeaderPadrao} from "../../../componentes/header/headerPadrao";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {Sidebar} from "../../../componentes/sidebar/sidebar";
import {Ajuda} from "../../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import Selector from "../../../componentes/Selector";
import {Titulos} from "../../../core/ENUM/Titulos";
import {useForm} from "react-hook-form";
import {
    buscarOrigem, buscarTitulosContabeis
} from "../../../logica/API/Receita/ReceitaAPI";
import {buscarBancoPorNome, buscarDadosBancariosPorBanco} from "../../../logica/API/ContaBancaria/ContaBancariaAPI";
import {
    buscarCategoriasDespesa,
    buscarContaInternaDespesa, buscarDespesaPorId,
    buscarTitulosContabeisDespesa
} from "../../../logica/API/Despesa/DespesaAPI";
import {validaDadosSubmissao} from "../../../logica/Validacoes/CadastroDespesaValidacao";

interface IFormInputs {
    contaInterna: string;
    categoria: string;
    tituloContabil: string;
    valorDespesa: number;
    dataDespesa: Date;
    beneficiario: string;
    categoriaTransacao: string;
    bancoOrigem: string;
    dadosBancariosOrigem: string;
    bancoDestino: string;
    agenciaDestino: string;
    numeroContaDestino: string;
    descricao: string;
}

export function CadastroDespesa() {
    let { id } = useParams();
    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [fetchDataComplete, setFetchDataComplete] = useState(false);
    const [configOrigem, setConfigOrigem] = useState<string>("");
    const [erro, setErro] = useState<boolean>(false);

    const [arrayContaInterna, setArrayContaInterna] = useState([]);
    const [arrayCategoria, setArrayCategoria] = useState([]);
    const [arrayTituloContabil, setArrayTituloContabil] = useState([]);
    const [arrayBancos, setArrayBancos] = useState([]);
    const [arrayDadosBancarios, setArrayDadosBancarios] = useState([]);
    const [arrayOrigem, setArrayOrigem] = useState([]);

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
                    buscarCategoriasDespesa(),
                    buscarContaInternaDespesa(),
                    buscarOrigem()
                ]);

                if (id != null || id != undefined) {
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
                    if (id != undefined) {
                        const [receita] = await Promise.all([
                            buscarDespesaPorId(id)
                        ]);
                        for (let prop in receita) {
                            setValue('contaInterna', receita[prop].contaInterna.nome)
                            setValue('categoria', receita[prop].categoria);
                            setValue('tituloContabil', receita[prop].titulo_contabil);
                            setValue('valorDespesa', receita[prop].valorDespesa);
                            setValue('dataDespesa', receita[prop].dataDespesa);
                            setValue('beneficiario', receita[prop].beneficiario);
                            setValue('categoriaTransacao', receita[prop].categoriaTransacao);
                            setValue('bancoOrigem', receita[prop].bancoOrigem);
                            setValue('dadosBancariosOrigem', receita[prop].dadosBancarios)
                            setValue('bancoDestino', receita[prop].bancoDestino);
                            setValue('agenciaDestino', receita[prop].agenciaDestino);
                            setValue('numeroContaDestino', receita[prop].numeroContaDestino);
                            setValue('descricao', receita[prop].descricao);

                            verificaOrigem = receita[prop].origem
                            verificaCategoria=receita[prop].categoriaTransacao

                            verificaDadosBancarios = receita[prop].bancoDestino
                        }
                        verificaDadoBancario(verificaDadosBancarios)
                        verificacaoCategorias(verificaCategoria);

                        const bancosCadastrados = await buscarBancoPorNome();
                        setArrayBancos(bancosCadastrados)
                        console.log("dados de origem: "+watch('categoriaTransacao'))
                        console.log("dados de bancarios: "+watch('dadosBancariosOrigem'))
                        if(watch('categoriaTransacao') === "Transferência" ) {
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

    const handleContaInterna = async (dado: any) => {
        try {
            setValue('contaInterna', dado.nome);
        } catch (error) {
            console.error('Erro ao salvar as Contas Internas', error)
        }
    };

    const handleCategoria= async (dado: any) => {
        try {
            const titulosContabeis = await buscarTitulosContabeisDespesa(dado.id);
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

    const onSubmit = async (data: IFormInputs) => {
        id = id || '';

        setErro(false);
        let validacaoResultado = validaDadosSubmissao(id, data.contaInterna,
            data.categoria,
            data.tituloContabil,
            data.dataDespesa,
            data.valorDespesa,
            data.categoriaTransacao,
            data.bancoOrigem,
            data.dadosBancariosOrigem,
            data.beneficiario,
            data.bancoDestino,
            data.agenciaDestino,
            data.numeroContaDestino,
            data.descricao);
        if (validacaoResultado) {
            console.error('Algum dos dados está nulo.');
            setErro(true);
            return;
        }

    };

    const handleOrigem = async (dado: any) => {
        const bancosCadastrados = await buscarBancoPorNome();
        setArrayBancos(bancosCadastrados)
        setValue('categoriaTransacao', dado.nome);
        if(dado.nome.toString() === "Transferência" ) {
            setConfigOrigem("Transferência")
        } else if(dado.nome.toString() === "Pix") {
            setConfigOrigem("Pix")
        } else {
            setConfigOrigem("false")
        }
    };

    const handleBancoOrigem = async (dado: any) => {
        setValue('bancoOrigem', dado.nome);
        const dadosBancariosRecebidos = await buscarDadosBancariosPorBanco(dado.nome)
        setArrayDadosBancarios(dadosBancariosRecebidos);
    };

    const handleGenericoDadosBancarios = async (dado: any) => {
        setValue('dadosBancariosOrigem', dado.nome);
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuarioLocalStorage}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Cadastro de Despesa </h1>
                        <Ajuda tipoAjuda={AjudaEnum.CADASTRO_DESPESA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="flex justify-center items-center w-full">
                        <div
                            className="p-5 mt-4 sm:w-11/12 md:w-11/12 lg:w-11/12 border-solid border-1 border-stone-200 border-t-2 border-b-2 rounded-xl shadow-xl">
                            <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                                {erro && (
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
                                        {...register('valorDespesa', {required: false})}
                                        placeholder={Titulos.INPUT_VALOR_DESPESA.toString()}
                                        type={"number"}
                                        value={watch('valorDespesa')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.valorDespesa && <p>Selecione o título contábil.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('dataDespesa', {required: false})}
                                        type="date"
                                        value={watch('dataDespesa') ? new Date(watch('dataDespesa')).toISOString().substr(0, 10) : ''}
                                        placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                        className="text-lg text-black input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                    {errors.dataDespesa && (<p>Insira a data da receita.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('beneficiario', {required: false})}
                                        placeholder={Titulos.INPUT_BENEFICIARIO.toString()}
                                        type={"text"}
                                        value={watch('beneficiario')}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>

                                    {errors.beneficiario && (<p>Insira o nome do beneficiário.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayOrigem}
                                              placeholder={Titulos.INPUT_ORIGEM.toString()}
                                              valorSelecionado={watch('categoriaTransacao')}
                                              onGenericoSelect={handleOrigem}
                                    />
                                </div>

                                {configOrigem === "Transferência" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={watch('bancoOrigem')}
                                                      onGenericoSelect={handleBancoOrigem}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <div className="inputs relative my-4">
                                                <Selector dado={arrayDadosBancarios}
                                                          placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                          valorSelecionado={watch('dadosBancariosOrigem')}
                                                          onGenericoSelect={handleGenericoDadosBancarios}/>
                                            </div>
                                        </div>

                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoDestino', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_DESTINO.toString()}
                                                type={"text"}
                                                value={watch('bancoDestino')}
                                                className="input-with-line w-full"/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('agenciaDestino', {required: false})}
                                                placeholder={Titulos.INPUT_AGENCIA_DESTINO.toString()}
                                                type={"text"}
                                                value={watch('agenciaDestino')}
                                                className="input-with-line w-full"/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('numeroContaDestino', {required: false})}
                                                placeholder={Titulos.INPUT_CONTA_DESTINO.toString()}
                                                type={"text"}
                                                value={watch('numeroContaDestino')}
                                                className="input-with-line w-full"/>
                                        </div>
                                    </>
                                )}

                                {configOrigem === "Pix" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={watch('bancoOrigem')}
                                                      onGenericoSelect={handleBancoOrigem}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <div className="inputs relative my-4">
                                                <Selector dado={arrayDadosBancarios}
                                                          placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                          valorSelecionado={watch('dadosBancariosOrigem')}
                                                          onGenericoSelect={handleGenericoDadosBancarios}/>
                                            </div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoDestino', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_DESTINO.toString()}
                                                type={"text"}
                                                value={watch('bancoDestino')}
                                                className="input-with-line w-full"/>
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
    )

}

export default CadastroDespesa;