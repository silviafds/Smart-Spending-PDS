package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.DTO.CategoriaReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.spel.spi.ReactiveEvaluationContextExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Modifying
    @Query("UPDATE receita r SET r.categoria = :categoria, r.titulo_contabil = :titulo_contabil, " +
            "r.dataReceita = :dataReceita, r.valorReceita = :valorReceita, r.origem = :origem, " +
            "r.bancoOrigem = :bancoOrigem, r.agenciaOrigem = :agenciaOrigem, r.numeroContaOrigem = :numeroContaOrigem, " +
            "r.bancoDestino = :bancoDestino, r.agenciaDestino = :agenciaDestino, r.numeroContaDestino = :numeroContaDestino, " +
            "r.descricao = :descricao, r.contaInterna = :contaInterna " +
            "WHERE r.id = :idReceita")
    int editarReceita(
            @Param("idReceita") Long idReceita,
            @Param("categoria") String categoria,
            @Param("titulo_contabil") String titulo_contabil,
            @Param("dataReceita") LocalDate dataReceita,
            @Param("valorReceita") Double valorReceita,
            @Param("origem") String origem,
            @Param("bancoOrigem") String bancoOrigem,
            @Param("agenciaOrigem") String agenciaOrigem,
            @Param("numeroContaOrigem") String numeroContaOrigem,
            @Param("bancoDestino") String bancoDestino,
            @Param("agenciaDestino") String agenciaDestino,
            @Param("numeroContaDestino") String numeroContaDestino,
            @Param("descricao") String descricao,
            @Param("contaInterna") ContaInterna contaInterna
    );


    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita(c.id, c.nome) FROM categoria_receita c")
    List<CategoriaReceita> findByAllCategoriaReceita();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita(tcr.id, tcr.nome) FROM titulos_contabeis_receita tcr WHERE tcr.categoriaReceita.id = :idCategoria")
    List<TituloContabilReceita> findByAllTitulosContabeisReceita(int idCategoria);

    @Query("SELECT r FROM receita r")
    List<Receita> returnAllReceitas();

    @Query("SELECT r FROM receita r WHERE r.id = :idReceita")
    List<Receita> findReceitaById(int idReceita);

}
