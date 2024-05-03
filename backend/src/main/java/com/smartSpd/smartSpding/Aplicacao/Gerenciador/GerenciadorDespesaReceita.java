package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GerenciadorDespesaReceita {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void verificaDTOIsNULL(BalancoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO == null ||
                balancoRapidoDTO.getNome() == null ||
                balancoRapidoDTO.getTipoBalanco() == null ||
                balancoRapidoDTO.getAnaliseBalanco() == null ||
                balancoRapidoDTO.getDataInicio() == null ||
                balancoRapidoDTO.getDataTermino() == null ||
                balancoRapidoDTO.getTipoVisualizacao() == null) {
            throw new NullPointerException("Um dos campos de balancoRapidoDespesaReceitaDTO está nulo.");
        }
    }

    public String queryBalanco(BalancoDTO balancoRapidoDTO) {
        String tipoBalanco = balancoRapidoDTO.getTipoBalanco();
        String categoriaOuTituloContabil = balancoRapidoDTO.getCategoriaOuTituloContabil();

        if (tipoBalanco.equals("Despesa")) {
            return buildQueryForDespesa(categoriaOuTituloContabil);
        } else if (tipoBalanco.equals("Receita")) {
            return buildQueryForReceita(categoriaOuTituloContabil);
        } else if (tipoBalanco.equals("Despesa e Receita")) {
            String queryDespesa = buildQueryForDespesa(categoriaOuTituloContabil);
            String queryReceita = buildQueryForReceita(categoriaOuTituloContabil);
            return queryDespesa + " UNION ALL " + queryReceita;
        }
        return "";
    }

    private String buildQueryForDespesa(String categoriaOuTituloContabil) {
        if (categoriaOuTituloContabil.equals("Categoria")) {
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
    }

    private String buildQueryForReceita(String categoriaOuTituloContabil) {
        if (categoriaOuTituloContabil.equals("Categoria")) {
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

    public List<BalancoDespesaReceita> montaQuery(String sql, BalancoDTO balancoRapidoDTO) {
        List<BalancoDespesaReceita> resultados = new ArrayList<>();

        try {
            return jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setDate(1, java.sql.Date.valueOf(balancoRapidoDTO.getDataInicio()));
                    preparedStatement.setDate(2, java.sql.Date.valueOf(balancoRapidoDTO.getDataTermino()));

                    if(balancoRapidoDTO.getTipoBalanco().equals("Despesa e Receita")) {
                        preparedStatement.setDate(3, java.sql.Date.valueOf(balancoRapidoDTO.getDataInicio()));
                        preparedStatement.setDate(4, java.sql.Date.valueOf(balancoRapidoDTO.getDataTermino()));
                    }

                }
            }, new ResultSetExtractor<List<BalancoDespesaReceita>>() {
                @Override
                public List<BalancoDespesaReceita> extractData(ResultSet rs) throws SQLException {
                    List<BalancoDespesaReceita> tempResultados = new ArrayList<>();
                    while (rs.next()) {
                        BalancoDespesaReceita balancoDespesaReceita = extrairBalancoDespesa(rs, balancoRapidoDTO);
                        tempResultados.add(balancoDespesaReceita);
                    }
                    return tempResultados;
                }
            });
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao consultar os meios de pagamnetos mais utilizados. ", e);
        }

        return resultados;
    }


    private BalancoDespesaReceita extrairBalancoDespesa(ResultSet rs, BalancoDTO balancoRapidoDTO) throws SQLException {
        String categoriaTransacao;
        if(balancoRapidoDTO.getCategoriaOuTituloContabil().equals("Categoria")) {
            categoriaTransacao = rs.getString("categoria");
        } else {
            categoriaTransacao = rs.getString("titulo_contabil");
        }

        Long valor = rs.getLong("valor");

        BalancoDespesaReceita balancoDespesaReceita = new BalancoDespesaReceita(categoriaTransacao, valor,
                balancoRapidoDTO.getCategoriaOuTituloContabil());

        balancoDespesaReceita.setNome(balancoRapidoDTO.getNome());
        balancoDespesaReceita.setTipoBalanco(balancoRapidoDTO.getTipoBalanco());
        balancoDespesaReceita.setAnaliseBalanco(balancoRapidoDTO.getAnaliseBalanco());
        balancoDespesaReceita.setDataInicio(balancoRapidoDTO.getDataInicio());
        balancoDespesaReceita.setDataTermino(balancoRapidoDTO.getDataTermino());
        balancoDespesaReceita.setTipoVisualizacao(balancoRapidoDTO.getTipoVisualizacao());

        return balancoDespesaReceita;
    }

    public void salvarBalancoDespesaReceita(BalancoDespesaReceita balancoDespesaReceita) {
        String sql = "INSERT INTO balanco (nome, valor, tipo_balanco, analise_balanco, data_inicio, data_termino, tipo_visualizacao, categoria_ou_titulo_contabil) VALUES (?, ROUND(?, 2), ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                balancoDespesaReceita.getNome(),
                balancoDespesaReceita.getValor(),
                balancoDespesaReceita.getTipoBalanco(),
                balancoDespesaReceita.getAnaliseBalanco(),
                balancoDespesaReceita.getDataInicio(),
                balancoDespesaReceita.getDataTermino(),
                balancoDespesaReceita.getTipoVisualizacao(),
                balancoDespesaReceita.getCategoriaOuTituloContabil()
        );
    }
}
