package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.DespesaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class DespesaBalancoImpl implements DespesaBalancoService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

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
        List<BalancoDespesa> resultados = new ArrayList<>();

        verificaDTO(balancoRapidoDTO);

        String sql = queryMeiosPagamento();

        try {
            return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(balancoRapidoDTO.getDataInicio()));
                    preparedStatement.setDate(2, java.sql.Date.valueOf(balancoRapidoDTO.getDataTermino()));
                }
            }, new ResultSetExtractor<List<BalancoDespesa>>() {
                @Override
                public List<BalancoDespesa> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        BalancoDespesa balancoDespesa = extrairBalancoDespesa(rs, balancoRapidoDTO);
                        resultados.add(balancoDespesa);
                    }
                    return resultados;
                }
            });
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao consultar os meios de pagamnetos mais utilizados. ", e);
        }

        return resultados;
    }

    private BalancoDespesa extrairBalancoDespesa(ResultSet rs, BalancoRapidoDTO balancoRapidoDTO) throws SQLException {
        String categoriaTransacao = rs.getString("categoria_transacao");
        Long valor = rs.getLong("valor");

        BalancoDespesa balancoDespesa = new BalancoDespesa(categoriaTransacao, valor);

        balancoDespesa.setNome(balancoRapidoDTO.getNome());
        balancoDespesa.setTipoBalanco(balancoRapidoDTO.getTipoBalanco());
        balancoDespesa.setAnaliseBalanco(balancoRapidoDTO.getAnaliseBalanco());
        balancoDespesa.setDataInicio(balancoRapidoDTO.getDataInicio());
        balancoDespesa.setDataTermino(balancoRapidoDTO.getDataTermino());
        balancoDespesa.setTipoVisualizacao(balancoRapidoDTO.getTipoVisualizacao());

        return balancoDespesa;
    }

    public String queryMeiosPagamento() {
        return  "SELECT categoria_transacao, COUNT(*) AS valor FROM despesa " +
                "WHERE categoria_transacao IS NOT NULL AND data_despesa BETWEEN ? AND ? " +
                "GROUP BY categoria_transacao ORDER BY valor DESC";
    }

    public void verificaDTO(BalancoRapidoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO == null ||
                balancoRapidoDTO.getNome() == null ||
                balancoRapidoDTO.getTipoBalanco() == null ||
                balancoRapidoDTO.getAnaliseBalanco() == null ||
                balancoRapidoDTO.getDataInicio() == null ||
                balancoRapidoDTO.getDataTermino() == null ||
                balancoRapidoDTO.getTipoVisualizacao() == null) {
            throw new NullPointerException("Um dos campos de balancoRapidoDTO está vazio.");
        }

    }

}