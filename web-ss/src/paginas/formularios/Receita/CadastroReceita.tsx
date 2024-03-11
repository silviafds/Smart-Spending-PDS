import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../../components/header/headerPadrao";
import { Sidebar } from "../../../components/sidebar/sidebar";
import { Ajuda } from "../../../components/ajuda/Ajuda";
import { AjudaEnum } from "../../../core/ENUM/Ajuda";
import { useForm } from "react-hook-form";
import { buscarContaInternaReceita, buscarCategoriasReceita, buscarTitulosContabeis,
    buscarOrigem, buscarReceitaPorId } from "../../../logica/API/Receita/ReceitaAPI";
import Selector from "../../../components/Selector";
import {verificaContaInterna} from "../../../logica/API/ContaInterna/ContaInternaAPI";
import {Titulos} from "../../../core/ENUM/Titulos";
import {buscarBancoPorNome, buscarDadosBancariosPorBanco} from "../../../logica/API/ContaBancaria/ContaBancariaAPI";
import {useParams} from "react-router-dom";

interface IFormInputs {
    contaInterna: string;
    categoria: string;
    tituloContabil: string;
    valorReceita: number;
    dataReceita: Date;
    origem: string;
    bancoOrigem: string;
    agenciaOrigem: string;
    contaOrigem: string;
    bancoDestino: string;
    agenciaDestino: string;
    contaDestino: string;
    descricao: string;
}

export function CadastroReceita() {
    let { id } = useParams();
    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [fetchDataComplete, setFetchDataComplete] = useState(false);

    const [arrayContaInterna, setArrayContaInterna] = useState([]);
    const [arrayCategoria, setArrayCategoria] = useState([]);
    const [arrayTituloContabil, setArrayTituloContabil] = useState([]);
    const [arrayOrigem, setArrayOrigem] = useState([]);
    const [arrayBancos, setArrayBancos] = useState([]);
    const [arrayDadosBancarios, setArrayDadosBancarios] = useState([]);

    const [contaInterna, setContaInterna] = useState<string>("");
    const [categoria, setCategoria] = useState<string>("");
    const [tituloContabil, setTituloContabil] = useState<string>("");
    const [origem, setOrigem] = useState<string>("");
    const [bancoOrigem, setBancoOrigem] = useState<string>("");
    const [bancoDestino, setBancoDestino] = useState<string>("");
    const [bancoOrigemSelecionado, setBancoOrigemSelecionado] = useState<string>("");
    const [dadosBancariosDestino, setDadosBancarios] = useState<string>("");

    const [valorReceita, setValorReceita] = useState<number>();
    const [dataReceita, setDataReceita] = useState<string>("");
    const [agenciaOrigem, setAgenciaOrigem] = useState<string>("");
    const [numeroContaOrigem, setNumeroContaOrigem] = useState<string>("");
    const [agenciaDestino, setAgenciaDestino] = useState<string>("");
    const [numeroContaDestino, setNumeroContaDestino] = useState<string>("");
    const [descricao, setDescricao] = useState<string>("");


    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuarioLocalStorage(storageUser);
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
                const [categorias, contasInternas, origens] = await Promise.all([
                    buscarCategoriasReceita(),
                    buscarContaInternaReceita(),
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
                            buscarReceitaPorId(id)
                        ]);
                        for (let prop in receita) {
                            setContaInterna(receita[prop].contaInterna.nome)
                            verificaCategoria = receita[prop].categoria;
                            setCategoria(receita[prop].categoria)
                            setTituloContabil(receita[prop].titulo_contabil)
                            setValorReceita(Number(receita[prop].valorReceita))
                            setDataReceita(receita[prop].dataReceita)
                            verificaOrigem = receita[prop].origem
                            setOrigem(receita[prop].origem)
                            setBancoOrigemSelecionado(receita[prop].bancoOrigem)
                            setAgenciaOrigem(receita[prop].agenciaOrigem)
                            setNumeroContaOrigem(receita[prop].numeroContaOrigem)
                            setBancoDestino(receita[prop].bancoDestino)
                            setAgenciaDestino(receita[prop].agenciaDestino)
                            setNumeroContaDestino(receita[prop].numeroContaDestino)
                            setDescricao(receita[prop].descricao)
                            setDadosBancarios(receita[prop].dadosBancarios)
                            verificaDadosBancarios = receita[prop].bancoDestino
                        }
                        verificaDadoBancario(verificaDadosBancarios)
                        verificacaoCategorias(verificaCategoria);

                        if(verificaOrigem === "Transferência" ) {
                            const bancosCadastrados = await buscarBancoPorNome();
                            setArrayBancos(bancosCadastrados)
                            setBancoOrigem("Transferência")
                        } else if(verificaOrigem === "Pix") {
                            const bancosCadastrados = await buscarBancoPorNome();
                            setArrayBancos(bancosCadastrados)
                            setBancoOrigem("Pix")
                        } else {
                            setBancoOrigem("false")
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
        chamadaDadosBancarios(idReceita);
    }
    const chamadaDadosBancarios = async (id: any) => {
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

        chamada(idReceita);
    }

    const chamada = async (id: any) => {
        try {
            const titulos = await buscarTitulosContabeis(id);
            setArrayTituloContabil(titulos);
        } catch (error) {
            console.error('Erro ao buscar os títulos contábeis', error);
        }
    };

    const handleContaInterna = async (dado: any) => {
        try {
            setContaInterna(dado.nome)
        } catch (error) {
            console.error('Erro ao salvar as Contas Internas', error)
        }
    };
    const handleCategoria= async (dado: any) => {
        try {
            const titulosContabeis = await buscarTitulosContabeis(dado.id);
            setArrayTituloContabil(titulosContabeis);
            setCategoria(dado.nome)
        } catch (error) {
            console.error('Erro ao buscar os títulos contábeis', error);
        }
    };
    const handleTituloContabil = async (dado: any) => {
        try {
            setTituloContabil(dado.nome)
        } catch (error) {
            console.error('Erro ao salvar titulo contabil', error)
        }
    };
    const handleOrigem = async (dado: any) => {
        if(dado.nome.toString() === "Transferência" ) {
            const bancosCadastrados = await buscarBancoPorNome();
            setArrayBancos(bancosCadastrados)
            setBancoOrigem("Transferência")
        } else if(dado.nome.toString() === "Pix") {
            const bancosCadastrados = await buscarBancoPorNome();
            setArrayBancos(bancosCadastrados)
            setBancoOrigem("Pix")
        } else {
            setBancoOrigem("false")
        }
        setOrigem(dado.nome)
    };
    const handleBancoDestino = async (dado: any) => {
        setBancoDestino(dado.nome)
        const dadosBancariosRecebidos = await buscarDadosBancariosPorBanco(dado.nome)
        setArrayDadosBancarios(dadosBancariosRecebidos);
    };
    const handleGenericoDadosBancarios = async (dado: any) => {
        setDadosBancarios(dado.nome);
    }

    const onSubmit = async (data: IFormInputs) => {
        const jsonData = {
            id: id || null,
            contaInterna,
            categoria,
            titulo_contabil: tituloContabil,
            dataReceita: id ? dataReceita : data.dataReceita,
            valorReceita: id ? valorReceita : data.valorReceita,
            origem,
            bancoOrigem: data.bancoOrigem,
            agenciaOrigem: data.agenciaOrigem,
            numeroContaOrigem: data.contaOrigem,
            bancoDestino,
            dadosBancariosDestino,
            descricao: id ? descricao : data.descricao
        };

        verificaContaInterna(jsonData, id ? "editarReceita" : "salvarReceita");
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
                                <div className="inputs relative my-4">
                                    <Selector dado={arrayContaInterna}
                                              placeholder={Titulos.INPUT_CONTA_INTERNA.toString()}
                                              valorSelecionado={contaInterna}
                                              onGenericoSelect={handleContaInterna}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayCategoria}
                                              placeholder={Titulos.INPUT_CATEGORIA.toString()}
                                              valorSelecionado={categoria}
                                              onGenericoSelect={handleCategoria}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayTituloContabil}
                                              placeholder={Titulos.INPUT_TITULO_CONTABIL.toString()}
                                              valorSelecionado={tituloContabil}
                                              onGenericoSelect={handleTituloContabil}/>
                                </div>

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('valorReceita', {required: false})}
                                        placeholder={Titulos.INPUT_VALOR_RECEITA.toString()}
                                        type={"number"}
                                        value={valorReceita}
                                        onChange={(e) => setValorReceita(parseFloat(e.target.value))}
                                        className="input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                </div>
                                {errors.valorReceita && <p>Selecione o título contábil.</p>}

                                <div className="inputs relative my-4">
                                    <input
                                        {...register('dataReceita', {required: false})}
                                        type="date"
                                        value={dataReceita}
                                        onChange={(e) => setDataReceita(e.target.value)}
                                        placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                        className="text-lg text-black input-with-line w-full"
                                    />
                                    <div className="line"></div>
                                    {errors.dataReceita && (<p>Insira a data da receita.</p>)}
                                </div>

                                <div className="inputs relative my-4">
                                    <Selector dado={arrayOrigem}
                                              placeholder={Titulos.INPUT_ORIGEM.toString()}
                                              valorSelecionado={origem}
                                              onGenericoSelect={handleOrigem}/>
                                </div>

                                {bancoOrigem === "Transferência" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_ORIGEM.toString()}
                                                type={"text"}
                                                value={bancoOrigemSelecionado}
                                                onChange={(e) => setBancoOrigemSelecionado(e.target.value)}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('agenciaOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_AGENCIA_ORIGEM.toString()}
                                                type={"text"}
                                                value={agenciaOrigem}
                                                onChange={(e) => setAgenciaOrigem(e.target.value)}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('contaOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_CONTA_ORIGEM.toString()}
                                                type={"text"}
                                                value={numeroContaOrigem}
                                                onChange={(e) => setNumeroContaOrigem(e.target.value)}
                                                className="input-with-line w-full"
                                            />
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={bancoDestino}
                                                      onGenericoSelect={handleBancoDestino}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayDadosBancarios}
                                                      placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                      valorSelecionado={dadosBancariosDestino}
                                                      onGenericoSelect={handleGenericoDadosBancarios}/>
                                        </div>
                                    </>
                                )}

                                {bancoOrigem === "Pix" && (
                                    <>
                                        <div className="inputs relative my-4">
                                            <input
                                                {...register('bancoOrigem', {required: false})}
                                                placeholder={Titulos.INPUT_BANCO_ORIGEM.toString()}
                                                type={"text"}
                                                value={bancoOrigemSelecionado}
                                                onChange={(e) => setBancoOrigemSelecionado(e.target.value)}
                                                className="input-with-line w-full"/>
                                            <div className="line"></div>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayBancos}
                                                      placeholder={Titulos.INPUT_BANCO.toString()}
                                                      valorSelecionado={bancoDestino}
                                                      onGenericoSelect={handleBancoDestino}/>
                                        </div>
                                        <div className="inputs relative my-4">
                                            <Selector dado={arrayDadosBancarios}
                                                      placeholder={Titulos.INPUT_DADOS_BANCARIOS.toString()}
                                                      valorSelecionado={dadosBancariosDestino}
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
                                        value={descricao}
                                        onChange={(e) => setDescricao(e.target.value)}
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