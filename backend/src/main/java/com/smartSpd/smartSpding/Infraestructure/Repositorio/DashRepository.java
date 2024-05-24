package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.repository.query.Param;

public interface DashRepository extends JpaRepository<Dash, Long> {

	@Query("SELECT d FROM dash d")
    List<Dash> buscarDashboard();

    @Query("SELECT b FROM balancos b where b.id = :identificador")
    List<Balancos> buscarBalancos(Long identificador);

    @Query("select count(1) from dash d where d.identicador_balanco = :id")
    int existsByIdenticadorBalanco(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM dash d WHERE d.identicador_balanco = :id")
    void removeBalancoDashboard(@Param("id") Long id);

}