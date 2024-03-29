package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DespesaBalancoImpl implements DespesaBalancoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BalancoDespesa> listagemMeiosPagamento() {
        String sql = "SELECT categoria_transacao, COUNT(*) AS quantidade_transacao FROM despesa " +
                "WHERE categoria_transacao IS NOT NULL " +
                "GROUP BY categoria_transacao ORDER BY quantidade_transacao DESC";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<BalancoDespesa>>() {
            @Override
            public List<BalancoDespesa> extractData(ResultSet rs) throws SQLException {
                List<BalancoDespesa> resultados = new ArrayList<>();
                while (rs.next()) {
                    String categoriaTransacao = rs.getString("categoria_transacao");
                    Long quantidadeTransacao = rs.getLong("quantidade_transacao");
                    resultados.add(new BalancoDespesa(categoriaTransacao, quantidadeTransacao));
                }
                return resultados;
            }
        });
    }

    @Override
    public List<BalancoDespesa> balancoMeiosPagamento(BalancoRapidoDTO balancoRapidoDTO) {
        String sql = "SELECT categoria_transacao, COUNT(*) AS quantidade_transacao FROM despesa " +
                "WHERE categoria_transacao IS NOT NULL AND data_despesa BETWEEN ? AND ? " +
                "GROUP BY categoria_transacao ORDER BY quantidade_transacao DESC";

        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setDate(1, java.sql.Date.valueOf(balancoRapidoDTO.getDataInicio()));
                preparedStatement.setDate(2, java.sql.Date.valueOf(balancoRapidoDTO.getDataTermino()));
            }
        }, new ResultSetExtractor<List<BalancoDespesa>>() {
            @Override
            public List<BalancoDespesa> extractData(ResultSet rs) throws SQLException {
                List<BalancoDespesa> resultados = new ArrayList<>();
                while (rs.next()) {
                    String categoriaTransacao = rs.getString("categoria_transacao");
                    Long quantidadeTransacao = rs.getLong("quantidade_transacao");
                    resultados.add(new BalancoDespesa(categoriaTransacao, quantidadeTransacao));
                }
                return resultados;
            }
        });
    }



}
