package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Modifying
    @Query("UPDATE despesa d SET d.categoria = :categoria, d.titulo_contabil = :titulo_contabil, " +
            "d.dataDespesa = :dataDespesa, d.valorDespesa = :valorDespesa, d.categoriaTransacao = :categoriaTransacao, " +
            "d.bancoOrigem = :bancoOrigem, d.tipoContaOrigem = :tipo_conta_origem, d.agenciaOrigem = :agenciaOrigem, " +
            "d.numeroContaOrigem = :numeroContaOrigem, d.beneficiario = :beneficiario, d.bancoDestino = :bancoDestino, " +
            "d.agenciaDestino = :agenciaDestino, d.numeroContaDestino = :numeroContaDestino, d.descricao = :descricao, d.contaInterna = :contaInterna " +
            "WHERE d.id = :idDespesa")
    int editarDespesa(
            @Param("idDespesa") Long idDespesa,
            @Param("categoria") String categoria,
            @Param("titulo_contabil") String titulo_contabil,
            @Param("dataDespesa") LocalDate dataDespesa,
            @Param("valorDespesa") Double valorDespesa,
            @Param("categoriaTransacao") String categoriaTransacao,
            @Param("bancoOrigem") String bancoOrigem,
            @Param("tipo_conta_origem") String tipo_conta_origem,
            @Param("agenciaOrigem") String agenciaOrigem,
            @Param("numeroContaOrigem") String numeroContaOrigem,
            @Param("beneficiario") String beneficiario,
            @Param("bancoDestino") String bancoDestino,
            @Param("agenciaDestino") String agenciaDestino,
            @Param("numeroContaDestino") String numeroContaDestino,
            @Param("descricao") String descricao,
            @Param("contaInterna") ContaInterna contaInterna
    );

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa(c.id, c.nome) FROM categoria_despesa c")
    List<CategoriaDespesa> buscarTodasAsCategoriaDespesa();

    @Query("SELECT d FROM despesa d")
    List<Despesa> buscarTodasDespesas();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.TituloContabilDespesa(tcd.id, tcd.nome) FROM titulos_contabeis_despesa tcd WHERE tcd.categoriaDespesa.id = :idCategoria")
    List<TituloContabilDespesa> findByAllTitulosContabeisDespesa(int idCategoria);

    @Query("SELECT d FROM despesa d WHERE d.id = :idDespesa")
    List<Despesa> buscarDespesaPorId(int idDespesa);

    @Query("SELECT r FROM despesa r WHERE r.contaInterna = :contaInterna")
    List<Despesa> findByContaInterna(ContaInterna contaInterna);

}