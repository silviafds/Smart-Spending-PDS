package com.smartSpd.smartSpding.Infraestructure.Repositorio;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaldoRepository {

    @Query("SELECT COALESCE(SUM(r.valorReceita), 0) - COALESCE(SUM(d.valorDespesa), 0)" +
            "FROM Receita r" +
            "LEFT JOIN r.contaInterna cr" +
            "LEFT JOIN Despesa d ON d.contaInterna = cr" +
            "WHERE cr.desabilitarConta = false AND cr.id = :idContaInterna")
    Double calcularSaldoPorContaInterna(@Param("idContaInterna") Long idContaInterna);
}
