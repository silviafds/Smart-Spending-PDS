package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.CHEQUE;
import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.PAPEL_E_MOEDA;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Modifying
    @Query("UPDATE receita r SET r.categoria = :categoria, r.titulo_contabil = :titulo_contabil, " +
            "r.dataReceita = :dataReceita, r.valorReceita = :valorReceita, r.origem = :origem, " +
            "r.bancoOrigem = :bancoOrigem, r.agenciaOrigem = :agenciaOrigem, r.numeroContaOrigem = :numeroContaOrigem, " +
            "r.bancoDestino = :bancoDestino, r.agenciaDestino = :agenciaDestino, r.numeroContaDestino = :numeroContaDestino, " +
            "r.descricao = :descricao, r.contaInterna = :contaInterna, r.tipoContaDestino = :tipo_conta_destino, " +
            "r.pagador = :pagador WHERE r.id = :idReceita")
    int editarReceita(
            @Param("idReceita") Long idReceita,
            @Param("categoria") String categoria,
            @Param("titulo_contabil") String titulo_contabil,
            @Param("dataReceita") LocalDate dataReceita,
            @Param("valorReceita") Double valorReceita,
            @Param("pagador") String pagador,
            @Param("origem") String origem,
            @Param("bancoOrigem") String bancoOrigem,
            @Param("agenciaOrigem") String agenciaOrigem,
            @Param("numeroContaOrigem") String numeroContaOrigem,
            @Param("bancoDestino") String bancoDestino,
            @Param("tipo_conta_destino") String tipo_conta_destino,
            @Param("agenciaDestino") String agenciaDestino,
            @Param("numeroContaDestino") String numeroContaDestino,
            @Param("descricao") String descricao,
            @Param("contaInterna") ContaInterna contaInterna
    );

    default int edicaoReceita(ReceitaDTO receitaDTO, String[] dadosReformulados) throws Exception {
        int rowsUpdated = 0;

        try {
            Receita receita = findById(receitaDTO.getId()).orElse(null);

            if (receita != null) {
                updateReceitaFields(receita, receitaDTO, dadosReformulados);
                save(receita);
                rowsUpdated = 1;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("Ocorreu um erro de NullPointerException durante a edição da receita.");
        }

        return rowsUpdated;
    }

    private void updateReceitaFields(Receita receita, ReceitaDTO receitaDTO, String[] dadosReformulados) {
        receita.setCategoria(receitaDTO.getCategoria());
        receita.setTitulo_contabil(receitaDTO.getTitulo_contabil());
        receita.setDataReceita(receitaDTO.getDataReceita());
        receita.setValorReceita(receitaDTO.getValorReceita());
        receita.setOrigem(receitaDTO.getOrigem());

        if (receitaDTO.getOrigem().equals(CHEQUE.getMeiosPagamento()) ||
                receitaDTO.getOrigem().equals(PAPEL_E_MOEDA.getMeiosPagamento())) {
        } else {
            receita.setBancoDestino(dadosReformulados[0]);
            receita.setAgenciaDestino(dadosReformulados[1]);
            receita.setNumeroContaDestino(dadosReformulados[2]);
        }

        receita.setNumeroContaOrigem(receitaDTO.getNumeroContaOrigem());
        receita.setPagador(receitaDTO.getPagador());
        receita.setBancoOrigem(receitaDTO.getBancoOrigem());
        receita.setAgenciaOrigem(receitaDTO.getAgenciaOrigem());
        receita.setNumeroContaOrigem(receitaDTO.getNumeroContaOrigem());
        receita.setDescricao(receitaDTO.getDescricao());
        receita.setContaInterna(receitaDTO.getContaInterna());
    }

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita(c.id, c.nome) FROM categoria_receita c")
    List<CategoriaReceita> buscarTodasAsCategoriaReceita();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita(tcr.id, tcr.nome) FROM titulos_contabeis_receita tcr WHERE tcr.categoriaReceita.id = :idCategoria")
    List<TituloContabilReceita> findByAllTitulosContabeisReceita(int idCategoria);

    @Query("SELECT r FROM receita r")
    List<Receita> buscarTodasReceitas();

    @Query("SELECT r FROM receita r WHERE r.id = :idReceita")
    List<Receita> buscarReceitaPorId(int idReceita);

    @Query("SELECT SUM(r.valorReceita) FROM receita r WHERE r.dataReceita BETWEEN :dataInicio AND :dataTermino")
    double totalReceitaPorPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                  @Param("dataTermino") LocalDate dataTermino);

    @Query(nativeQuery = true,
            value = "SELECT r.categoria, SUM(valor_receita) FROM receita r " +
                    "WHERE data_receita BETWEEN :startDate AND :endDate " +
                    "GROUP BY categoria " +
                    "ORDER BY SUM(valor_receita) DESC " +
                    "LIMIT 3")
    List<Object[]> encontrarReceitasPorCategoria(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);


}