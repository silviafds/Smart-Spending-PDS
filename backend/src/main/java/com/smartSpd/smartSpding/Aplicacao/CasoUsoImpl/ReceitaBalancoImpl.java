package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
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
public class ReceitaBalancoImpl implements ReceitaBalancoService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Autowired
    private JdbcTemplate jdbcTemplate;


   /* @Override
    public List<BalancoReceita> listagemMeiosPagamento() {
        String sql = "SELECT origem, COUNT(*) AS quantidade_transacao FROM receita " +
                "WHERE categoria_transacao IS NOT NULL " +
                "GROUP BY categoria_transacao ORDER BY quantidade_transacao DESC";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<BalancoReceita>>() {
            @Override
            public List<BalancoReceita> extractData(ResultSet rs) throws SQLException {
                List<BalancoReceita> resultados = new ArrayList<>();
                while (rs.next()) {
                    String categoriaTransacao = rs.getString("categoria_transacao");
                    Long quantidadeTransacao = rs.getLong("quantidade_transacao");
                    resultados.add(new BalancoReceita(categoriaTransacao, quantidadeTransacao));
                }
                return resultados;
            }
        });
    }*/

    @Override
    public List<BalancoReceita> balancoMeiosPagamento(BalancoDTO balancoRapidoDTO) {
        List<BalancoReceita> resultados = new ArrayList<>();

        verificaDTO(balancoRapidoDTO);

        String sql = queryMeiosPagamento();

        try {
            return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(balancoRapidoDTO.getDataInicio()));
                    preparedStatement.setDate(2, java.sql.Date.valueOf(balancoRapidoDTO.getDataTermino()));
                }
            }, new ResultSetExtractor<List<BalancoReceita>>() {
                @Override
                public List<BalancoReceita> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        BalancoReceita balancoDespesa = extrairBalancoReceita(rs, balancoRapidoDTO);
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

    private BalancoReceita extrairBalancoReceita(ResultSet rs, BalancoDTO balancoRapidoDTO) throws SQLException {
        String categoriaTransacao = rs.getString("origem");
        Long valor = rs.getLong("valor");

        BalancoReceita balancoReceita = new BalancoReceita(categoriaTransacao, valor);

        balancoReceita.setNome(balancoRapidoDTO.getNome());
        balancoReceita.setTipoBalanco(balancoRapidoDTO.getTipoBalanco());
        balancoReceita.setAnaliseBalanco(balancoRapidoDTO.getAnaliseBalanco());
        balancoReceita.setDataInicio(balancoRapidoDTO.getDataInicio());
        balancoReceita.setDataTermino(balancoRapidoDTO.getDataTermino());
        balancoReceita.setTipoVisualizacao(balancoRapidoDTO.getTipoVisualizacao());

        return balancoReceita;
    }

    public String queryMeiosPagamento() {
        return  "SELECT origem, COUNT(*) AS valor FROM receita " +
                "WHERE origem IS NOT NULL AND data_receita BETWEEN ? AND ? " +
                "GROUP BY origem ORDER BY valor DESC";
    }

    public void verificaDTO(BalancoDTO balancoRapidoDTO) {
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
