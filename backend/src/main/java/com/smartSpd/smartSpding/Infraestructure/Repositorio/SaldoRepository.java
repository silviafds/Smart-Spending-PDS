package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaldoRepository extends JpaRepository<ContaInterna, Long> {

    @Query("SELECT c.nome FROM ContaInterna c WHERE c.id = :id")
    String findNomeById(@Param("id") Long id);

    @Query("SELECT c.id FROM ContaInterna c WHERE c.desabilitarConta = false")
    List<Long> findAllIdsContaInterna();

    @Query("SELECT c.id, COALESCE(SUM(r.valorReceita), 0) - COALESCE(SUM(d.valorDespesa), 0) " +
            "FROM receita r " +
            "JOIN r.contaInterna c " +
            "LEFT JOIN despesa d ON c.id = d.contaInterna.id " +
            "WHERE c.desabilitarConta = false " +
            "GROUP BY c.id")
    List<Object[]> calcularSaldoPorContaHabilitada();

}
