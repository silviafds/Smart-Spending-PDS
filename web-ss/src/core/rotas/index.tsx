import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { Login } from "../../paginas/usuario/login";
import CadastroUsuario from '../../paginas/usuario/cadastroUsuario';
import { RotaPrivada } from "./rotaPrivada";
import Dashboard from "../../paginas/Dashboard/dashboard";
import Receita from "../../paginas/Lancamento/Receita/Receita"
import Despesa from "../../paginas/Lancamento/Despesa/Despesa"
import Projetos from "../../paginas/Projeto/Projetos"
import Balanco from "../../paginas/Balanco/LancamentoBalanco/Balanco";
import ContaInterna from "../../paginas/ContaInterna/ContaInterna";
import CadastroContaInterna from "../../paginas/ContaInterna/CadastroContaInterna";
import EditarContaInterna from "../../paginas/ContaInterna/EditarContaInterna";
import CadastroReceita from "../../paginas/Lancamento/Receita/CadastroReceita";
import CadastroDespesa from "../../paginas/Lancamento/Despesa/CadastroDespesa";
import ContaBancaria from "../../paginas/ContaBancaria/ContaBancaria";
import CadastroContaBancaria from "../../paginas/ContaBancaria/CadastroContaBancaria";
import EditarContaBancaria from "../../paginas/ContaBancaria/EditarContaBancaria";
import LancamentoBalanco from "../../paginas/Balanco/LancamentoBalanco/LancamentoBalanco";
import ProjetosBalanco from "../../paginas/Balanco/ProjetoBalanco/ProjetosBalanco";
import TelaConfiguracao from "../../paginas/Configuracao/TelaConfiguracao";
import TelaCategoria from "../../paginas/Categoria/TelaCategoria";
import CadastroCategoria from "../../paginas/Categoria/CadastroCategoria";
import TelaBalancoRapido from "../../paginas/Balanco/LancamentoBalanco/TelaBalancoRapido";
import Conselhos from "../../paginas/Conselhos/Conselhos";
import CadastroProjeto from "../../paginas/Projeto/CadastroProjeto";

export const AppRouter = () => {
    return (
        <Router>
            <Routes>

                <Route path="/" element={<Login />} />
                <Route path="/cadastrarUsuario" element={<CadastroUsuario />} />

                <Route path="/dashboard" element={<RotaPrivada />} >
                    <Route path="/dashboard" element={<Dashboard />} />
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

                <Route path="/projetos" element={<RotaPrivada />} >
                    <Route path="/projetos" element={<Projetos />} />
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

                <Route path="/ProjetosBalanco" element={<RotaPrivada />} >
                    <Route path="/ProjetosBalanco" element={<ProjetosBalanco />} />
                </Route>

                <Route path="/TelaConfiguracao" element={<RotaPrivada />} >
                    <Route path="/TelaConfiguracao" element={<TelaConfiguracao />} />
                </Route>

                <Route path="/TelaCategoria" element={<RotaPrivada />} >
                    <Route path="/TelaCategoria" element={<TelaCategoria />} />
                </Route>

                <Route path="/cadastroCategoria" element={<RotaPrivada />} >
                    <Route path="/cadastroCategoria/:id?" element={<CadastroCategoria />} />
                </Route>

                <Route path="/BalancoRapido" element={<RotaPrivada />} >
                    <Route path="/BalancoRapido" element={<TelaBalancoRapido />} />
                </Route>

                <Route path="/GerenciadorConselhos" element={<RotaPrivada />} >
                    <Route path="/GerenciadorConselhos" element={<Conselhos />} />
                </Route>

                <Route path="/CadastroProjeto" element={<RotaPrivada />} >
                    <Route path="/CadastroProjeto/:id?" element={<CadastroProjeto />} />
                </Route>
            </Routes>
        </Router>
    );
};