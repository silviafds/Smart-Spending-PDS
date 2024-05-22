package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DashRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DashboardRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.DESPESA;
import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.RECEITA;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private BalancosRepository balancoRepository;

    @Autowired
    private DashRepository dashRepository;

    @Autowired
    private DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    private DespesaBalancoService despesaBalancoService;

    @Autowired
    private ReceitaBalancoService receitaBalancoService;

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Override
    public String salvarBalancoDashboard(Long id) {
        try {
            if (id != null) {
                Optional<Balancos> balancos = balancoRepository.findById(id);
                if (balancos.isPresent()) {
                    Dash dash = new Dash();
                    dash.setIdenticador_balanco(id);
                    dashRepository.save(dash);
                    return "Balanço salvo no dashboard.";
                } else {
                    return "Não existe este balanço na base de dados.";
                }
            }
            return "Id está nulo.";
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao salvar o balanço no dashboard: " + e.getMessage());
            return "Ocorreu um erro ao salvar o balanço no dashboard.";
        }
    }

    @Override
    public List<List<?>> buscarBalancosDashboard() {
        List<Dash> lista = dashRepository.buscarDashboard();

        List<Balancos> resultados = new ArrayList<>();
        List<List<?>> resultadosProcessamentos = new ArrayList<>();

        for (Dash dash : lista) {
            List<Balancos> resultado = dashRepository.buscarBalancos(dash.getIdenticador_balanco());
            resultados.addAll(resultado);
        }

        for (Balancos resultado : resultados) {
            BalancoRapidoDTO dto = convertToDTO(resultado);
            List<?> result = executarBalancos(dto);
            resultadosProcessamentos.add(result);
        }

        return resultadosProcessamentos;
    }

    public List<?> executarBalancos(BalancoRapidoDTO dto) {
        List<BalancoDespesaReceita> dados = new ArrayList<>();
        if(dto.getTipoBalanco().equals(DESPESA_RECEITA.getTiposBalanco())) {
            dados = despesaReceitaBalancoService.buscarDadosReceitaDespesa(dto);
        }

        if(dto.getTipoBalanco().equals(DESPESA.getBalanco())) {
            if(dto.getAnaliseBalanco().equals(BUSCAR_TODAS_DESPESAS.getTiposBalanco())) {
                dados = despesaReceitaBalancoService.buscarDadosReceitaDespesa(dto);
            } else {
                return despesaBalancoService.balancoMeiosPagamento(dto);
            }
        }

        if(dto.getTipoBalanco().equals(RECEITA.getBalanco())) {
            if(dto.getAnaliseBalanco().equals(BUSCAR_TODAS_RECEITAS.getTiposBalanco())) {
                return despesaReceitaBalancoService.buscarDadosReceitaDespesa(dto);
            } else {
                return receitaBalancoService.balancoMeiosPagamento(dto);

            }
        }
        return dados;
    }

    private static BalancoRapidoDTO convertToDTO(Balancos balanco) {

        return new BalancoRapidoDTO(
                balanco.getNome(),
                balanco.getTipoBalanco(),
                balanco.getAnalise_balanco(),
                balanco.getData_inicio() != null ? balanco.getData_inicio() : null,
                balanco.getData_termino() != null ? balanco.getData_termino() : null,
                balanco.getTipo_visualizacao(),
                balanco.getCategoria_titulo_contabil()
        );
    }

    @Override
    public void createDashboard(String nome, Set<Balancos> balancos) {
        try {
            if (nome == null || nome.isEmpty()) {
                throw new IllegalArgumentException("O nome do dashboard não pode ser nulo ou vazio.");
            }

            Dashboard dashboard = new Dashboard(nome, balancos);
            dashboardRepository.save(dashboard);
        } catch (IllegalArgumentException e) {
            log.warning("Nome do dashboard é inválido: " + nome);
            throw new IllegalArgumentException("O nome do dashboard é inválido.", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao criar dashboard.", e);
            throw new RuntimeException("Erro ao criar dashboard.", e);
        }
    }

    @Override
    public void addBalanco(Balancos balanco) {
        try {
            if (balanco == null) {
                throw new IllegalArgumentException("O balanço não pode ser nulo.");
            }

            Dashboard dashboard = dashboardRepository.buscarDashboard();
            dashboard.getBalancos().add(balanco);
            balanco.setDashboard(dashboard);
            dashboardRepository.save(dashboard);
            balancoRepository.save(balanco);
        } catch (IllegalArgumentException e) {
            log.warning("Erro nos parâmetros de entrada: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao adicionar balanço ao dashboard.", e);
            throw new RuntimeException("Erro ao adicionar balanço ao dashboard.", e);
        }
    }
    
    @Override
    public void removeBalanco(Balancos balanco) {
        try {
            if (balanco == null) {
                throw new IllegalArgumentException("O balanço não pode ser nulo.");
            }

            Dashboard dashboard = dashboardRepository.buscarDashboard();
            if (!dashboard.getBalancos().remove(balanco)) {
                throw new BalancoNaoEncontradoException("O balanço não está associado a este dashboard.");
            }
            balanco.setDashboard(null);
            dashboardRepository.save(dashboard);
            balancoRepository.save(balanco);
        } catch (IllegalArgumentException e) {
            log.warning("Erro nos parâmetros de entrada: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao remover balanço do dashboard.", e);
            throw new RuntimeException("Erro ao remover balanço do dashboard.", e);
        }
    }
    
    @Override
    public void deletarDashboard() {
        try {
            dashboardRepository.deleteAll();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar o dashboard.", e);
            throw new RuntimeException("Erro ao deletar o dashboard.", e);
        }
    }
    
    @Override
    public Dashboard buscarDashboard() {
        try {
            return dashboardRepository.buscarDashboard();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar o dashboard.", e);
            return null;
        }
    }

}
