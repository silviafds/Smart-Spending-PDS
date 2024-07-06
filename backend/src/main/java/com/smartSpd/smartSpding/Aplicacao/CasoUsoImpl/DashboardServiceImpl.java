package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.CasoUso.DashboardService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DashRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.DESPESA;
import static com.smartSpd.smartSpding.Core.Enum.BalancoEnum.RECEITA;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.*;

@Service
public class DashboardServiceImpl implements DashboardService {

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
    public List<Dash> buscarBalancosNoDashboard() {
        return dashRepository.buscarDashboard();
    }

    @Override
    public String salvarBalancoDashboard(DashDTO dto) {
        if (dto.getIdenticador_balanco() == null) {
            throw new IllegalArgumentException("Id está nulo.");
        }

        Optional<Balancos> balanco = balancoRepository.findById(dto.getIdenticador_balanco());
        if (balanco.isPresent()) {
            int validador = dashRepository.existsByIdenticadorBalanco(dto.getIdenticador_balanco());
            if(validador != 1) {
                Dash dash = new Dash();
                dash.setIdenticador_balanco(dto.getIdenticador_balanco());
                dashRepository.save(dash);
                return "Balanço salvo no dashboard.";
            }
            return "Não foi possivel salvar no dashboard.";
        } else {
            throw new BalancoNaoEncontradoException("Não existe este balanço na base de dados.");
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
    
    @Override
    public String deletarBalancoDashboard(DashDTO dto) {
        try {
            if (dto.getIdenticador_balanco() != null) {
                int valida = dashRepository.existsByIdenticadorBalanco(dto.getIdenticador_balanco());
                if(valida==1) {
                    dashRepository.removeBalancoDashboard(dto.getIdenticador_balanco());
                    return "Balanço deletado do dashboard.";
                } else {
                    throw new BalancoNaoEncontradoException("Balanço não encontrado no dashboard.");
                }
            }
            return "Id está nulo.";
        } catch (BalancoNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao deletar o balanço do dashboard: " + e.getMessage());
            return "Ocorreu um erro ao deletar o balanço do dashboard.";
        }
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
                balanco.getCategoria_titulo_contabil(),
                null
        );
    }

}