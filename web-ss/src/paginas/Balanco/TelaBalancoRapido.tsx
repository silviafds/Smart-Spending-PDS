import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import  Loading  from "../../componentes/Loading";
import GraficoColunaVertical from "../../componentes/Grafico/GraficoColunaVertical";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { useLocation } from 'react-router-dom';
import {useForm} from "react-hook-form";
import {criarBalancoRapidoDespesa} from "../../logica/API/Despesa/BalancoDespesa";
import Swal from "sweetalert2";
import GraficoDeDonut from "../../componentes/Grafico/GraficoDeDonut";
import GraficoDePizza from "../../componentes/Grafico/GraficoDePizza";

interface IFormInputs {
    nomeBalanco: string;
    nomeBalancoAnalise: string;
    nome: string;
    tipoBalanco: string;
    analiseBalanco: string;
    dataInicio: Date;
    dataTermino: Date;
    tipoVisualizacao: string;
    categoriaOuTituloContabil: string;
}

export function TelaBalancoRapido() {
    const [loading, setLoading] = useState(true);
    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [startDate, setStartDate] = useState<Date | null>(null);
    const [endDate, setEndDate] = useState<Date | null>(null);
    const location = useLocation();
    const dados = JSON.parse(new URLSearchParams(location.search).get("dados") as string);

    useEffect(() => {
        setTimeout(() => {
            setLoading(false);
        }, 2000);
    }, []);

    const {
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuarioLocalStorage(storageUser);
        }
    }, []);

    // @ts-ignore
    useEffect(() => {
        for (let prop in dados) {
            setValue("nome", dados[prop].nome)
            setValue("analiseBalanco", dados[prop].analiseBalanco)
            setValue("tipoBalanco", dados[prop].tipoBalanco)
            setValue("dataInicio", dados[prop].dataInicio)
            setValue("dataTermino", dados[prop].dataTermino)
            setValue("tipoVisualizacao", dados[prop].tipoVisualizacao)
            setValue("categoriaOuTituloContabil", dados[prop].categoriaOuTituloContabil)
            const dataInicio = parseDate(dados[prop].dataInicio);
            setStartDate(dataInicio);
            setEndDate(new Date(dados[prop].dataTermino));
        }
    }, []);

    function parseDate(dateString: { split: (arg0: string) => [any, any, any]; }) {
        const [year, month, day] = dateString.split('-');
        return new Date(year, month - 1, day); // O mês é zero-indexed
    }

    const handleDataInicial = (date: Date | null | any) => {
        setStartDate(date);
        for (let prop in dados) {
            setValue("nome", dados[prop].nome)
            setValue('analiseBalanco', dados[prop].analiseBalanco);
            setValue('nome', dados[prop].nome);
            setValue('tipoBalanco', dados[prop].tipoBalanco);
            setValue('dataInicio', dados[prop].dataInicio);
            setValue('dataTermino', dados[prop].dataTermino);
            setValue("categoriaOuTituloContabil", dados[prop].categoriaOuTituloContabil)
        }
    };

    const handleDataFinal = (date: Date | null | any) => {
        if (startDate && date < startDate) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: 'A data final não pode ser anterior à data de início.',
                customClass: {
                    confirmButton: 'error-button'
                }
            });
            return;
        }
        setEndDate(date);
        if(startDate != null) {
            setValue('dataInicio', startDate);
            setValue('dataTermino', date);

            const jsonData = {
                nome: watch('nome'),
                tipoBalanco: watch('tipoBalanco'),
                analiseBalanco: watch('analiseBalanco'),
                dataInicio: watch('dataInicio'),
                dataTermino: watch('dataTermino'),
                tipoVisualizacao: watch('tipoVisualizacao'),
                categoriaOuTituloContabil: watch('categoriaOuTituloContabil')
            };
            criarBalancoRapidoDespesa(jsonData);
        }
    };

    return (
        <div>
            {loading ? (
                <Loading/>
            ) : (
                <div>
                    <div>
                        <HeaderPadrao nomeUsuario={nomeUsuarioLocalStorage}/>
                        <div className={"flex"}>
                            <Sidebar/>
                            <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                                <div className={"flex justify-between"}>
                                    <h1 className={"text-2xl font-semibold"}> Balanço Rápido </h1>
                                    <Ajuda tipoAjuda={AjudaEnum.CADASTRO_BALANCO_RAPIDO}/>
                                </div>
                                <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                                <div className="mt-10 w-full max-w-6xl flex flex-col items-center">
                                    <div className="flex justify-between w-full">
                                        <div>
                                            <h1 className="mb-4 font-bold text-2xl">{watch('nome')}</h1>
                                            <h1 className="mb-8 font-light text-lg">{watch('analiseBalanco')}</h1>
                                        </div>
                                        <div className="flex">
                                            <DatePicker
                                                selected={startDate}
                                                onChange={handleDataInicial}
                                                dateFormat="dd/MM/yyyy"
                                                placeholderText="Data Início"
                                                className="w-full bg-white border border-gray-300 rounded px-3 py-2
                                                focus:outline-none focus:border-blue-500"
                                                isClearable
                                                showYearDropdown
                                                scrollableYearDropdown
                                            />
                                            <DatePicker
                                                selected={endDate}
                                                onChange={handleDataFinal}
                                                dateFormat="dd/MM/yyyy"
                                                placeholderText="Data Final"
                                                className="w-full bg-white border border-gray-300 rounded px-3 py-2
                                                ml-6 focus:outline-none focus:border-blue-500"
                                                isClearable
                                                showYearDropdown
                                                scrollableYearDropdown
                                            />
                                        </div>
                                    </div>
                                    {watch('tipoVisualizacao') === 'Gráfico em Colunas' && (
                                        <GraficoColunaVertical data={dados} />
                                    )}
                                    {watch('tipoVisualizacao') === 'Gráfico de Pizza' && (
                                        <GraficoDePizza data={dados} />
                                    )}
                                    {watch('tipoVisualizacao') === 'Gráfico de Donut' && (
                                        <GraficoDeDonut data={dados} />
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default TelaBalancoRapido;