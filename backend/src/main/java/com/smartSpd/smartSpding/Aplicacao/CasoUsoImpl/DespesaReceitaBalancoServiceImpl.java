package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorDespesaReceita;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDespesaReceitaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class DespesaReceitaBalancoServiceImpl implements DespesaReceitaBalancoService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private GerenciadorDespesaReceita gerenciadorDespesaReceita;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BalancoDespesaReceita> buscarDadosReceitaDespesa(BalancoRapidoDespesaReceitaDTO balancoRapidoDespesaReceitaDTO) {
        String sql;
        List<BalancoDespesaReceita> resultados = new ArrayList<>();

        gerenciadorDespesaReceita.verificaDTOIsNULL(balancoRapidoDespesaReceitaDTO);

        sql = gerenciadorDespesaReceita.queryBalanco(balancoRapidoDespesaReceitaDTO);
        resultados.addAll(gerenciadorDespesaReceita.montaQuery(sql, balancoRapidoDespesaReceitaDTO));

        return resultados;
    }




     /*private String queryBalanco(BalancoRapidoDespesaReceitaDTO balanco) {
        if (balanco.getTipoBalanco().equals("Despesa")) {
            if(balanco.getCategoriaOuTituloContabil().equals("Categoria")) {
                return "SELECT categoria, SUM(valor_despesa) AS valor " +
                        "FROM despesa " +
                        "WHERE data_despesa IS NOT NULL AND data_despesa BETWEEN ? AND ? " +
                        "GROUP BY categoria";
            } else {
                return "SELECT titulo_contabil, SUM(valor_despesa) AS valor " +
                        "FROM despesa " +
                        "WHERE data_despesa IS NOT NULL AND data_despesa BETWEEN ? AND ? " +
                        "GROUP BY titulo_contabil";
            }
        } else if (balanco.getTipoBalanco().equals("Receita")) {
            if(balanco.getCategoriaOuTituloContabil().equals("Categoria")) {
                return "SELECT categoria, SUM(valor_receita) AS valor " +
                        "FROM receita " +
                        "WHERE data_receita IS NOT NULL AND data_receita BETWEEN ? AND ? " +
                        "GROUP BY categoria";
            } else {
                return "SELECT titulo_contabil, SUM(valor_receita) AS valor " +
                        "FROM receita " +
                        "WHERE data_receita IS NOT NULL AND data_receita BETWEEN ? AND ? " +
                        "GROUP BY titulo_contabil";
            }
        }
        return "";
    }*/
}
