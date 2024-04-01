package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.MeioOrigemService;
import com.smartSpd.smartSpding.Core.Classes.MeioOrigem;
import com.smartSpd.smartSpding.Core.DTO.MeioOrigemDTO;
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
public class MeioOrigemServiceImpl implements MeioOrigemService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<MeioOrigem> listagemMeiosOrigem() {
        String sql = "SELECT origem, COUNT(*) AS quantidade_transacao FROM despesa " +
                "WHERE categoria_transacao IS NOT NULL " +
                "GROUP BY categoria_transacao ORDER BY quantidade_transacao DESC";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<MeioOrigem>>() {
            @Override
            public List<MeioOrigem> extractData(ResultSet rs) throws SQLException {
                List<MeioOrigem> resultados = new ArrayList<>();
                while (rs.next()) {
                    String categoriaTransacao = rs.getString("categoria_transacao");
                    Long quantidadeTransacao = rs.getLong("quantidade_transacao");
                    resultados.add(new MeioOrigem(categoriaTransacao, quantidadeTransacao));
                }
                return resultados;
            }
        });
    }

    @Override
    public List<MeioOrigem> balancoMeioOrigem(MeioOrigemDTO meioOrigemDTO) {
        List<MeioOrigem> resultados = new ArrayList<>();

        verificaDTO(meioOrigemDTO);

        String sql = queryMeiosPagamento();

        try {
            return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(meioOrigemDTO.getDataInicio()));
                    preparedStatement.setDate(2, java.sql.Date.valueOf(meioOrigemDTO.getDataTermino()));
                }
            }, new ResultSetExtractor<List<MeioOrigem>>() {
                @Override
                public List<MeioOrigem> extractData(ResultSet rs) throws SQLException {
                    while (rs.next()) {
                        MeioOrigem balancoMeioOrigem = extrairBalancoMeioOrigem(rs, meioOrigemDTO);
                        resultados.add(balancoMeioOrigem);
                    }
                    return resultados;
                }
            });
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao consultar os meios de pagamento mais utilizados. ", e);
        }

        return resultados;
    }

    private MeioOrigem extrairBalancoMeioOrigem(ResultSet rs, MeioOrigemDTO meioOrigemDTO) throws SQLException {
        String categoriaTransacao = rs.getString("categoria_transacao");
        Long valor = rs.getLong("valor");

        MeioOrigem meioOrigem = new MeioOrigem(categoriaTransacao, valor);

        meioOrigem.setNome(meioOrigemDTO.getNome());
        meioOrigem.setTipoBalanco(meioOrigemDTO.getTipoOrigem());
        meioOrigem.setDataInicio(balancoRapidoDTO.getDataInicio());
        meioOrigem.setDataTermino(balancoRapidoDTO.getDataTermino());
        meioOrigem.setTipoVisualizacao(balancoRapidoDTO.getTipoVisualizacao());

        return meioOrigem;
    }

    public String queryMeiosPagamento() {
        return  "SELECT categoria_transacao, COUNT(*) AS valor FROM despesa " +
                "WHERE categoria_transacao IS NOT NULL AND data_despesa BETWEEN ? AND ? " +
                "GROUP BY categoria_transacao ORDER BY valor DESC";
    }

    public void verificaDTO(MeioOrigemDTO meioOrigemDTO) {
        if (meioOrigemDTO == null ||
        		meioOrigemDTO.getNome() == null ||
        		meioOrigemDTO.getTipoOrigem() == null ||
        		meioOrigemDTO.getDataInicio() == null ||
        		meioOrigemDTO.getDataTermino() == null ||
        		meioOrigemDTO.getTipoVisualizacao() == null) {
            throw new NullPointerException("Um dos campos de meioOrigemDTO está vazio.");
        }

    }

}
