import React, { useEffect, useState } from 'react';
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import GraficoColunaVertical from '../../componentes/Grafico/GraficoColunaVertical';
import GraficoDePizza from '../../componentes/Grafico/GraficoDePizza';
import GraficoDeDonut from '../../componentes/Grafico/GraficoDeDonut';
import { buscarBalanco, buscarBalancoPorId } from "../../logica/API/BalancoAPI";
import Swal from 'sweetalert2';
import { criarDashboard } from '../../logica/API/DashboardAPI';

function Dashboard() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [selectedVisualization, setSelectedVisualization] = useState<string>("Gráfico em Colunas");
    const [data, setData] = useState<any[]>([]); // Estado para armazenar os balanços
    const [loading, setLoading] = useState<boolean>(true); // Estado para controlar o carregamento dos dados

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }

        // Obter os balanços do banco de dados quando o componente montar
        fetchData();
    }, []);

    // Função para obter os balanços do banco de dados
    const fetchData = async () => {
        try {
            const balancos = await buscarBalanco();
            setData(balancos);
            setLoading(false); // Indica que o carregamento dos dados foi concluído
        } catch (error) {
            console.error('Erro ao carregar os balanços', error);
        }
    };

    const handleVisualizationChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedVisualization(event.target.value);
    };

    const handleCreateDashboard = async () => {
        try {
            const jsonString = {
                id: 1, // Exemplo de um Long id
                nome: "Dashboard 1" // Exemplo de um string nome
            };
            
            await criarDashboard(jsonString, () => {
                // Callback para fechar o modal ou fazer outras ações necessárias após criar o dashboard
                Swal.fire({
                    icon: "success",
                    title: "Dashboard criado com sucesso.",
                    showConfirmButton: true,
                    confirmButtonColor: "#072e66",
                    confirmButtonText: "OK",
                    customClass: {
                        confirmButton: "bg-sky-950",
                    },
                });
                // Após criar o dashboard com sucesso, atualiza os balanços
                fetchData();
            });
        } catch (error) {
            console.error("Erro ao criar dashboard:", error);
        }
    };

    return (
        <div className="cadastro-usuario">
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <Sidebar />
            <div>
                <label htmlFor="visualization">Tipo de Visualização:</label>
                <select id="visualization" value={selectedVisualization} onChange={handleVisualizationChange}>
                    <option value="Gráfico em Colunas">Gráfico em Colunas</option>
                    <option value="Gráfico de Pizza">Gráfico de Pizza</option>
                    <option value="Gráfico de Donut">Gráfico de Donut</option>
                </select>
            </div>
            {loading ? (
                <p>Carregando...</p> // Mostra uma mensagem de carregamento enquanto os dados estão sendo buscados
            ) : (
                <>
                    {/* Renderizando o componente de gráfico com base na seleção */}
                    {selectedVisualization === "Gráfico em Colunas" && (
                        <GraficoColunaVertical data={data} />
                    )}
                    {selectedVisualization === "Gráfico de Pizza" && (
                        <GraficoDePizza data={data} />
                    )}
                    {selectedVisualization === "Gráfico de Donut" && (
                        <GraficoDeDonut data={data} />
                    )}
                </>
            )}
            <button onClick={handleCreateDashboard}>Criar Dashboard</button>
        </div>
    );
}

export default Dashboard;
