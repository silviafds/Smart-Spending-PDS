package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface SaldoRepository extends JpaRepository<ContaInterna, Long> {

    @Query("SELECT c.id FROM ContaInterna c WHERE c.desabilitarConta = false")
    List<Long> findAllIdsContaInterna();

    @Query("SELECT ci.id, COALESCE(SUM(r.valorReceita), 0) - COALESCE(SUM(d.valorDespesa), 0) " +
            "FROM receita r " +
            "JOIN r.contaInterna ci " +
            "LEFT JOIN despesa d ON ci.id = d.contaInterna.id " +
            "WHERE ci.desabilitarConta = false")
    Double calcularSaldoPorContaHabilitada();

}
