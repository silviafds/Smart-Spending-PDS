package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BalancosRepository extends JpaRepository<Balancos, Long> {

    @Query("SELECT b FROM balancos b")
    List<Balancos> buscarTodosBalancos();

    @Modifying
    @Transactional
    @Query("UPDATE balancos b SET b.dashboard_check = :isDashboard WHERE b.id = :id")
    void updateDashboardCheck(@Param("isDashboard") boolean isDashboard, @Param("id") Long id);

    @Query("SELECT b.categoria_titulo_contabil, SUM(b.valor) FROM balancos b WHERE b.tipoBalanco = 'Despesa' GROUP BY b.categoria_titulo_contabil")
    List<Object[]> somarGastosPorCategoria();

    @Query("SELECT b.categoria_titulo_contabil, SUM(b.valor) FROM balancos b WHERE b.tipoBalanco = 'Investimento' GROUP BY b.categoria_titulo_contabil")
    List<Object[]> somarInvestimentosPorCategoria();

}