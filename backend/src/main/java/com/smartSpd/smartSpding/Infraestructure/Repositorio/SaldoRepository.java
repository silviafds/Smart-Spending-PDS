package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaldoRepository extends JpaRepository<ContaInterna, Long> {

    @Query("SELECT ci.nome, d.valorDespesa " +
            "FROM despesa d " +
            "JOIN d.contaInterna ci " +
            "WHERE ci.desabilitarConta = false")
    List<Object[]> listaTodasAsDespesas();

    @Query("SELECT ci.nome, r.valorReceita " +
            "FROM receita r " +
            "JOIN r.contaInterna ci " +
            "WHERE ci.desabilitarConta = false")
    List<Object[]> listaTodasAsReceita();

}