import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { Login } from "../paginas/usuario/login";
import CadastroUsuario from '../paginas/usuario/cadastroUsuario';

import { RotaPrivada } from "./rotaPrivada";
import Home from "../paginas/Principal/home";
import Receita from "../paginas/Principal/Receita"
import Despesa from "../paginas/Principal/Despesa"
import Metas from "../paginas/Principal/Metas";
import Projetos from "../paginas/Principal/Projetos"
import Patrimonio from "../paginas/Principal/Patrimonio"
import Balanco from "../paginas/Principal/Balanco";
import Dashboard from "../paginas/Principal/Dashboard";
import ContaInterna from "../paginas/Principal/ContaInterna";
import CadastroContaInterna from "../paginas/Formularios/ContaInterna/CadastroContaInterna";
import EditarContaInterna from "../paginas/Formularios/ContaInterna/EditarContaInterna";
import CadastroReceita from "../paginas/Formularios/Receita/CadastroReceita";
import CadastroDespesa from "../paginas/Formularios/Despesa/CadastroDespesa";
import ContaBancaria from "../paginas/Principal/ContaBancaria";
import CadastroContaBancaria from "../paginas/Formularios/ContaBancaria/CadastroContaBancaria";
import EditarContaBancaria from "../paginas/Formularios/ContaBancaria/EditarContaBancaria";
import LancamentoBalanco from "../paginas/Balanco/LancamentoBalanco";
import MetasBalanco from "../paginas/Balanco/MetasBalanco";
import ProjetosBalanco from "../paginas/Balanco/ProjetosBalanco";
import TelaConfiguracao from "../paginas/Principal/TelaConfiguracao";
import TelaCategoria from "../paginas/Configuracao/Categoria/TelaCategoria";

export const AppRouter = () => {
    return (
        <Router>
            <Routes>

                <Route path="/" element={<Login />} />
                <Route path="/cadastrarUsuario" element={<CadastroUsuario />} />

                <Route path="/home" element={<RotaPrivada />} >
                    <Route path="/home" element={<Home />} />
                </Route>

                <Route path="/receita" element={<RotaPrivada />} >
                    <Route path="/receita" element={<Receita />} />
                </Route>

                <Route path="/cadastroReceita" element={<RotaPrivada />} >
                    <Route path="/cadastroReceita/:id?" element={<CadastroReceita />} />
                </Route>

                <Route path="/cadastroDespesa" element={<RotaPrivada />} >
                    <Route path="/cadastroDespesa/:id?" element={<CadastroDespesa />} />
                </Route>

                <Route path="/despesa" element={<RotaPrivada />} >
                    <Route path="/despesa" element={<Despesa />} />
                </Route>

                <Route path="/metas" element={<RotaPrivada />} >
                    <Route path="/metas" element={<Metas />} />
                </Route>

                <Route path="/projetos" element={<RotaPrivada />} >
                    <Route path="/projetos" element={<Projetos />} />
                </Route>

                <Route path="/patrimonio" element={<RotaPrivada />} >
                    <Route path="/patrimonio" element={<Patrimonio />} />
                </Route>

                <Route path="/balanco" element={<RotaPrivada />} >
                    <Route path="/balanco" element={<Balanco />} />
                </Route>

                <Route path="/dashboard" element={<RotaPrivada />} >
                    <Route path="/dashboard" element={<Dashboard />} />
                </Route>

                <Route path="/contaInterna" element={<RotaPrivada />} >
                    <Route path="/contaInterna" element={<ContaInterna />} />
                </Route>

                <Route path="/cadastroContaInterna" element={<RotaPrivada />} >
                    <Route path="/cadastroContaInterna" element={<CadastroContaInterna />} />
                </Route>

                <Route path="/editarContaInterna/:idParaEditar" element={<RotaPrivada />} >
                    <Route path="/editarContaInterna/:idParaEditar" element={<EditarContaInterna />} />
                </Route>

                <Route path="/contaBancaria" element={<RotaPrivada />} >
                    <Route path="/contaBancaria" element={<ContaBancaria />} />
                </Route>

                <Route path="/cadastroContaBancaria" element={<RotaPrivada />} >
                    <Route path="/cadastroContaBancaria" element={<CadastroContaBancaria />} />
                </Route>

                <Route path="/editarContaBancaria/:idParaEditar" element={<RotaPrivada />} >
                    <Route path="/editarContaBancaria/:idParaEditar" element={<EditarContaBancaria />} />
                </Route>

                <Route path="/LancamentoBalanco" element={<RotaPrivada />} >
                    <Route path="/LancamentoBalanco" element={<LancamentoBalanco />} />
                </Route>

                <Route path="/MetasBalanco" element={<RotaPrivada />} >
                    <Route path="/MetasBalanco" element={<MetasBalanco />} />
                </Route>

                <Route path="/ProjetosBalanco" element={<RotaPrivada />} >
                    <Route path="/ProjetosBalanco" element={<ProjetosBalanco />} />
                </Route>

                <Route path="/TelaConfiguracao" element={<TelaConfiguracao />} >
                    <Route path="/TelaConfiguracao" element={<TelaConfiguracao />} />
                </Route>

                <Route path="/TelaCategoria" element={<TelaCategoria />} >
                    <Route path="/TelaCategoria" element={<TelaCategoria />} />
                </Route>

            </Routes>
        </Router>
    );
};