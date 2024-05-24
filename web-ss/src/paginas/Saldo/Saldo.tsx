import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import { Link } from 'react-router-dom';
import { buscarSaldo } from "../../logica/API/SaldoAPI";

function Saldo() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [saldoMap, setSaldoMap] = useState<Map<string, number>>();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const saldosObjeto = await buscarSaldo();
                const saldosArray: [string, number][] = Object.entries(saldosObjeto).map(([conta, saldo]) => [conta, saldo as number]);
                const saldosMap = new Map<string, number>(saldosArray);
                setSaldoMap(saldosMap);
            } catch (error) {
                console.error('Erro ao carregar saldos', error);
            }
        };
        fetchData();
    }, []);

    const renderSaldo = (conta: string) => {
        if (!saldoMap) return null;
        const valor = saldoMap.get(conta) ?? 0;
        const corSaldo = valor >= 0 ? "text-green-600" : "text-red-600";
        return (
            <Link to="" className="flex flex-col items-center justify-center" key={conta}>
                <div className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500 rounded-md drop-shadow-md hover:bg-gray-200">
                    <h2 className="text-center mt-16 font-semibold text-xl">{`Saldo ${conta}`}</h2>
                    <div className="flex items-center justify-center mt-2">
                        <h2 className={`text-center mt-0 font-semibold text-xl ${corSaldo}`}>{valor.toFixed(2)} R$</h2>
                    </div>
                </div>
            </Link>
        );
    };
    
    

    const totalSaldo = saldoMap ? Array.from(saldoMap.values()).reduce((acc, val) => acc + val, 0) : 0;
    const corTotalSaldo = totalSaldo >= 0 ? "text-green-600" : "text-red-600";

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <div className="flex">
                <Sidebar />
                <div className="border-solid border border-b-stone-200 w-screen p-7">
                    <div className="flex justify-between">
                        <h1 className="text-2xl font-semibold">Saldo</h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_ANALISE} />
                    </div>
                    <hr className="my-4 mt-6 p-0 w-full border-gray-300" />
                    <div className="mt-10 w-full max-w-6xl ">
                        <div className="grid grid-cols-2 gap-4">
                            {saldoMap && Array.from(saldoMap.keys()).map(renderSaldo)}
                            <div className="w-48 h-48 bg-gray-100 m-4 border-solid border-slate-500 rounded-md drop-shadow-md hover:bg-gray-200">
                                <h2 className="text-center mt-16 font-semibold text-xl">Saldo Total</h2>
                                <div className="flex items-center justify-center mt-2">
                                    <h2 className={`text-center mt-0 font-semibold text-xl ${corTotalSaldo}`}>{totalSaldo.toFixed(2)} R$</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Saldo;