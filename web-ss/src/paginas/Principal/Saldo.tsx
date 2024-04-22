import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../componentes/header/headerPadrao";
import {Sidebar} from "../../componentes/sidebar/sidebar";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";
import { Link } from 'react-router-dom';
import {MdOutlineAccountTree, MdOutlineSubtitles} from "react-icons/md";
import { BiMessageRoundedCheck } from "react-icons/bi";
import { TbCategoryPlus } from "react-icons/tb";

import {BsBank} from "react-icons/bs";
import { buscarSaldos } from "../../logica/API/SaldoAPI";

function Saldo() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [saldo, setSaldoMap] = useState<Map<string, number> | null>(null);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);
    
        useEffect(() => {
            const fetchData = async () => {
                try {
                    const [saldos] = await Promise.all([
                        buscarSaldos()
                    ]);
                    const saldosMap = new Map<string, number>(saldos as [string, number][]);
                    setSaldoMap(saldosMap); // Set the saldo value in state
                } catch (error) {
                    console.error('Erro ao carregar saldos', error);
                }
            };
    
            fetchData();
        }, []);


        const teste = saldo ?? new Map<string, number>();
        
        // map de saldos estaticos (podem ser dinamicos)

        teste.set('conta1', 200.2);
        teste.set('conta2', 500.39);
        teste.set('conta3', 600.28);

        const valor = (teste?.get('conta1') ?? 0) as number;
        const valor2 = (teste?.get('conta2') ?? 0) as number;
        const valor3 = (teste?.get('conta3') ?? 0) as number;


    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Saldo </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_ANALISE}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="mt-10 w-full max-w-6xl ">

                        <div className="flex justify-center items-center">

                            <Link to="" className="flex flex-col items-center justify-center">
                                <div
                                    className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500	rounded-md drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl	" >Saldo total</h2>
                                    <div
                                        className="flex items-center justify-center mt-2">
                                        <h2 className="text-center mt-0 font-semibold text-xl	">{valor+valor2+valor3} R$</h2>
                                    </div>
                                </div>
                            </Link>

                            <Link to="" className="flex flex-col items-center justify-center">
                                <div
                                    className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500	rounded-md drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl	">Saldo conta 1</h2>
                                    <div
                                        className="flex items-center justify-center mt-2">
                                        <h2 className="text-center mt-0 font-semibold text-xl	">{valor} R$</h2>
                                    </div>
                                </div>
                            </Link>

                        </div>

                        <div className="flex justify-center items-center">

                        <Link to="" className="flex flex-col items-center justify-center">
                                <div
                                    className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500	rounded-md drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl	">Saldo conta 2</h2>
                                    <div
                                        className="flex items-center justify-center mt-2">
                                        <h2 className="text-center mt-0 font-semibold text-xl	">{valor2} R$</h2>
                                    </div>
                                </div>
                            </Link>

                            <Link to="" className="flex flex-col items-center justify-center">
                                <div
                                    className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500	rounded-md drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl	">Saldo conta 3</h2>
                                    <div
                                        className="flex items-center justify-center mt-2">
                                        <h2 className="text-center mt-0 font-semibold text-xl	">{valor3} R$</h2>
                                    </div>
                                </div>
                            </Link>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
}

export default Saldo;