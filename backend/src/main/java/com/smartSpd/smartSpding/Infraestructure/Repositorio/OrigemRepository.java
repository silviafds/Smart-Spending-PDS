package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrigemRepository extends JpaRepository<Origem, Long> {

    @Query("SELECT f FROM origem f")
    List<Origem> findByAllOrigem();

   /* @Modifying
    @Query("UPDATE receita r SET r.categoria = :categoria, r.titulo_contabil = :titulo_contabil, " +
            "r.dataReceita = :novaDataReceita, r.valorReceita = :novoValorReceita, r.bancoReceita = :novoBancoReceita, " +
            "r.agenciaReceita = :novaAgenciaReceita, r.contaReceita = :novaContaReceita, r.descricao = :novaDescricao, " +
            "r.contaInterna = :novaContaInterna " +
            "WHERE r.id = :idDaReceita")
    int editarReceita(
            Long idDaReceita,
            String categoria,
            String titulo_contabil,
            LocalDate novaDataReceita,
            Double novoValorReceita,
            String novoBancoReceita,
            float novaAgenciaReceita,
            float novaContaReceita,
            String novaDescricao,
            ContaInterna novaContaInterna
    );*/



   /* @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita(tcr.id, tcr.nome) FROM titulos_contabeis_receita tcr WHERE tcr.categoriaReceita.id = :idCategoria")
    List<TituloContabilReceita> findByAllTitulosContabeisReceita(int idCategoria);*/

}
