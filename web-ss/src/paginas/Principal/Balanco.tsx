import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../componentes/header/headerPadrao";
import {Sidebar} from "../../componentes/sidebar/sidebar";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";
import { IoWalletOutline } from "react-icons/io5";
import { TbPigMoney } from "react-icons/tb";
import { Link } from 'react-router-dom'; // Importe o Link do react-router-dom


function Balanco() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);


    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Gerenciador de Balanços </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_ANALISE}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="mt-10 w-full max-w-6xl ">

                        <div className="flex justify-center items-center">

                            <Link to="/LancamentoBalanco" className="flex flex-col items-center justify-center ">
                                <div className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500 rounded-md
                                drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl">Lançamento</h2>
                                    <div className="flex items-center justify-center mt-2">
                                        <IoWalletOutline className="h-8 w-8" />
                                    </div>
                                </div>
                            </Link>

                            <Link to="/ProjetosBalanco" className="flex flex-col items-center justify-center ">
                                <div className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500	rounded-md
                                drop-shadow-md hover:bg-gray-200">
                                    <h2 className="text-center mt-16 font-semibold text-xl	">Projetos</h2>
                                    <div
                                        className="flex items-center justify-center mt-2">
                                        <TbPigMoney className="h-8 w-8"/>
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

export default Balanco;