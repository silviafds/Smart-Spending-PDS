import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import GraficoColunaVertical from "../../componentes/Grafico/GraficoColunaVertical";
import { AjudaEnum } from "../../core/ENUM/Ajuda";

/*
import TextField, { FilledTextFieldProps, OutlinedTextFieldProps, StandardTextFieldProps, TextFieldVariants } from '@mui/material/TextField';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import { JSX } from "react/jsx-runtime";
import AdapterDateFns from '@mui/x-date-pickers/AdapterDateFns';
import { TextFieldProps } from '@mui/material/TextField';
*/

/*import DataComponente from "../../componentes/DataComponente";*/
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { useLocation } from 'react-router-dom';
import {useForm} from "react-hook-form";


const chartData = [
    { name: 'Mon', value: 120 },
    { name: 'Tue', value: 200 },
    { name: 'Wed', value: 150 },
    { name: 'Thu', value: 80 },
    { name: 'Fri', value: 70 },
    { name: 'Sat', value: 110 },
    { name: 'Sun', value: 130 }
];

interface IFormInputs {
    nomeBalanco: string;
    nomeBalancoAnalise: string;
}

export function TelaBalancoRapido() {
    const [nomeUsuarioLocalStorage, setNomeUsuarioLocalStorage] = useState<string>("");
    const [value, setValues] = useState<Date | null>(new Date());
    const [selectedDate, setSelectedDate] = useState<Date | null>(null); // Alterado o tipo para Date | null
    const [startDate, setStartDate] = useState(null);
    const [endDate, setEndDate] = useState(null);
    const location = useLocation();
    const dados = JSON.parse(new URLSearchParams(location.search).get("dados") as string);

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
            setNomeUsuarioLocalStorage(storageUser);
        }
    }, []);

    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const dados = urlParams.get('dados');
        const nomeBalancoUrl = urlParams.get('nomeBalanco');
        const nomeBalancoAnaliseUrl = urlParams.get('nomeBalancoAnalise');
        setValue('nomeBalanco', nomeBalancoUrl || ''); // Se nomeBalancoUrl for null ou undefined, define como string vazia
        setValue('nomeBalancoAnalise', nomeBalancoAnaliseUrl || '');
        console.log('Dados:', dados);
        console.log('Outra Variável:', nomeBalancoUrl);
        console.log('Outra Variável:', nomeBalancoAnaliseUrl);


    }, []);

    const handleDateChange = (date: Date | null) => { // Ajustado o tipo do parâmetro para Date | null
        setSelectedDate(date);
    };
    function handleCadastro() {
        // Implemente a lógica aqui
    }

    const handleStartDateChange = (date: Date | null | any) => {
        setStartDate(date);
        for (let prop in dados) {
            console.log("categoria_transacao: "+dados[prop].categoria_transacao)
            console.log("quantidade_transacao: "+dados[prop].quantidade_transacao)
        }
    };

    const handleEndDateChange = (date: Date | null | any) => {
        if (startDate && date < startDate) {
            alert('A data final não pode ser anterior à data de início.');
            return;
        }
        setEndDate(date);
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuarioLocalStorage} />
            <div className={"flex"}>
                <Sidebar />
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Balanço Rápido </h1>
                        <Ajuda tipoAjuda={AjudaEnum.CADASTRO_BALANCO_RAPIDO}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>
                    {/*<div className="flex">
                        <DatePicker
                            selected={startDate}
                            onChange={handleStartDateChange}
                            dateFormat="dd/MM/yyyy"
                            placeholderText="Data Início"
                            className="w-full bg-white border border-gray-300 rounded px-3 py-2 focus:outline-none focus:border-blue-500"
                            isClearable
                            showYearDropdown
                            scrollableYearDropdown
                        />
                        <DatePicker
                            selected={endDate}
                            onChange={handleEndDateChange}
                            dateFormat="dd/MM/yyyy"
                            placeholderText="Data Final"
                            className="w-full bg-white border border-gray-300 rounded px-3 py-2 ml-6 focus:outline-none focus:border-blue-500"
                            isClearable
                            showYearDropdown
                            scrollableYearDropdown
                        />
                    </div>

                    <div className="text-left mt-6">
                        <h1 className="mb-4 font-bold text-2xl text-left">Nome do Balanço</h1>
                        <h1 className="mb-8 font-light text-lg text-left">Nome do tipo do balanço</h1>
                    </div>*/}

                    <div className="mt-10 w-full max-w-6xl flex flex-col items-center">
                        <div className="flex justify-between w-full">
                            <div>
                                <h1 className="mb-4 font-bold text-2xl">{watch('nomeBalanco')}</h1>
                                <h1 className="mb-8 font-light text-lg">{watch('nomeBalancoAnalise')}</h1>
                            </div>
                            <div className="flex">
                                <DatePicker
                                    selected={startDate}
                                    onChange={handleStartDateChange}
                                    dateFormat="dd/MM/yyyy"
                                    placeholderText="Data Início"
                                    className="w-full bg-white border border-gray-300 rounded px-3 py-2 focus:outline-none focus:border-blue-500"
                                    isClearable
                                    showYearDropdown
                                    scrollableYearDropdown
                                />
                                <DatePicker
                                    selected={endDate}
                                    onChange={handleEndDateChange}
                                    dateFormat="dd/MM/yyyy"
                                    placeholderText="Data Final"
                                    className="w-full bg-white border border-gray-300 rounded px-3 py-2 ml-6 focus:outline-none focus:border-blue-500"
                                    isClearable
                                    showYearDropdown
                                    scrollableYearDropdown
                                />
                            </div>
                        </div>
                        <GraficoColunaVertical data={dados}/>
                        <h1>Gráfico aqui</h1>
                    </div>


                </div>
            </div>
        </div>
    );
}

export default TelaBalancoRapido;