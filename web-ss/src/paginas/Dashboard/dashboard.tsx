import React, { useEffect, useState } from 'react';
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import {buscarDashboard} from '../../logica/API/DashboardAPI';
import GraficoColunaVertical from "../../componentes/Grafico/GraficoColunaVertical";
import GraficoDeDonut from "../../componentes/Grafico/GraficoDeDonut";
import GraficoDePizza from "../../componentes/Grafico/GraficoDePizza";

function Dashboard() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [selectedVisualization, setSelectedVisualization] = useState<string>("Gráfico em Colunas");
    const [data, setData] = useState<any[]>([]);
    const [loading, setLoading] = useState<boolean>(true); // Estado para controlar o carregamento dos dados

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const balancos = await buscarDashboard();
            if (Array.isArray(balancos)) {
                if (balancos.length > 0 && Array.isArray(balancos[0])) {
                    setData(balancos);
                } else {
                    setData([balancos]);
                }
            }
            setLoading(false);
            console.log("balancos:", balancos);
        } catch (error) {
            console.error('Erro ao carregar os balanços', error);
        }
    };

    const renderizarGrafico = (grupo: any[]) => {
        switch (grupo[0].tipoVisualizacao) {
            case 'Gráfico em Colunas':
                return <GraficoColunaVertical data={grupo} />;
            case 'Gráfico de Donut':
                return <GraficoDeDonut data={grupo} />;
            case 'Gráfico de Pizza':
                return <GraficoDePizza data={grupo} />;
            default:
                return null;
        }
    }


    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    {data.map((grupo, index) => (
                        <div key={index} className="mb-8 border-[1px] p-5 rounded-md">
                            <h2 className="text-xl font-semibold mb-4">{grupo[0].nome}</h2>
                            <h2 className="font-light text-lg mb-4">{grupo[0].tipoBalanco}</h2>
                            {renderizarGrafico(grupo)}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
