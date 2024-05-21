package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DashboardRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private BalancosRepository balancoRepository;
    
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Override
    public void createDashboard(String nome, Balancos balanco) {
        try {
            if (nome == null || nome.isEmpty()) {
                throw new IllegalArgumentException("O nome do dashboard não pode ser nulo ou vazio.");
            }

            Dashboard dashboard = new Dashboard(nome, balanco);
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
    public Dashboard buscarDashboard() {
        try {
            return dashboardRepository.buscarDashboard();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os dashboards.", e);
            return null;
        }
    }

}
