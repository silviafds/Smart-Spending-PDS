package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BalancosRepository extends JpaRepository<Balancos, Long> {

    @Query("SELECT b FROM balancos b")
    List<Balancos> buscarTodosBalancos();


}
