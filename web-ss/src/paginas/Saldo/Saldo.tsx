import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
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
            <div className="w-11/12 h-44 bg-gray-100 m-4 border-solid border-slate-500 rounded-md drop-shadow-md text-center" key={conta}>
                <h2 className="mt-16 font-semibold text-xl">{`${conta}`}</h2>
                <div className="mt-2">
                    <h2 className={`font-semibold text-xl ${corSaldo}`}>R$ {valor.toFixed(2)}</h2>
                </div>
            </div>
        );
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <div className="flex justify-center">
                <Sidebar />
                <div className="border-solid border border-b-stone-200 w-screen p-7">
                    <div className="flex justify-between">
                        <h1 className="text-2xl font-semibold">Saldo</h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_ANALISE} />
                    </div>
                    <hr className="my-4 mt-6 p-0 w-full border-gray-300" />
                    <div className="flex flex-col justify-center items-center mt-10">
                        <div className="flex flex-col justify-center items-center w-11/12 ">
                            {saldoMap && Array.from(saldoMap.keys()).map(renderSaldo)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Saldo;

