package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import ch.qos.logback.core.hook.DefaultShutdownHook;
import com.smartSpd.smartSpding.Apresentacao.Controller.BalancoController;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.DESPESA_RECEITA;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private BalancosRepository balancoRepository;

    @Autowired
    private DashRepository dashRepository;

    @Autowired
    private BalancoController balancoController;

    @Autowired
    private DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;
    
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
    public List<BalancoDespesaReceita> buscarBalancosDashboard() {
        List<Balancos> balancosEncontrado = new ArrayList<>();
        List<BalancoDespesaReceita> balancoDashboard = new ArrayList<>();
        List<Dash> lista = dashRepository.buscarDashboard();

        System.out.println("Número de itens na lista: " + lista.size());

     /*   for(int i = 0; i<lista.size(); i++) {
            List<Balancos> balancosEncontrados = dashRepository.buscarBalancos(lista.get(i).getIdenticador_balanco());

            System.out.println("total de balanços identificados: "+balancosEncontrados.size()+" id: "+lista.get(i).getIdenticador_balanco());
        }*/

        for (Dash dash : lista) {
            try {
                List<Balancos> balancosEncontrados = dashRepository.buscarBalancos(dash.getIdenticador_balanco());
                System.out.println("Balanços retornados para identificador " + dash.getIdenticador_balanco() + ": " + balancosEncontrados);
                System.out.println("total de balanços identificados: "+balancosEncontrados.size());
                for (Balancos balanco : balancosEncontrados) {
                    BalancoRapidoDTO dto = convertToDTO(balanco);
                    balancoDashboard = executarBalancos(dto);
                }
            } catch (Exception e) {
                System.err.println("Ocorreu um erro ao buscar os balanços para o identificador " + dash.getIdenticador_balanco() + ": " + e.getMessage());
            }
        }

        return balancoDashboard;
    }

    public List<BalancoDespesaReceita> executarBalancos(BalancoRapidoDTO dto) {
        List<BalancoDespesaReceita> dados = new ArrayList<>();
        Map<String, List<BalancoDespesaReceita>> mapaBalanco = new HashMap<>();

        if(dto.getTipoBalanco().equals(DESPESA_RECEITA.getTiposBalanco())) {
            dados = despesaReceitaBalancoService.buscarDadosReceitaDespesa(dto);
        }
        return dados;
    }

    private BalancoRapidoDTO convertToDTO(Balancos balanco) {
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
