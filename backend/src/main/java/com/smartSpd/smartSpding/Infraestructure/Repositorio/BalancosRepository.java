package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BalancosRepository extends JpaRepository<Balancos, Long> {

    @Query("SELECT b FROM balancos b where b.hospital_check != true and b.supermercado_check != true and b.restaurante_check != true")
    List<Balancos> buscarTodosBalancos();

    @Query("SELECT b FROM balancos b where b.hospital_check = true")
    List<Balancos> buscarTodosBalancosHospital();

    @Query("SELECT b FROM balancos b where b.supermercado_check = true")
    List<Balancos> buscarTodosBalancosSupermercado();

    @Query("SELECT b FROM balancos b where b.restaurante_check = true")
    List<Balancos> buscarTodosBalancosRestaurante();

    @Modifying
    @Transactional
    @Query("UPDATE balancos b SET b.dashboard_check = :isDashboard WHERE b.id = :id")
    void updateDashboardCheck(@Param("isDashboard") boolean isDashboard, @Param("id") Long id);

}