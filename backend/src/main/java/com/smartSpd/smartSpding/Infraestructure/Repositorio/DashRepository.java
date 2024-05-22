package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashRepository extends JpaRepository<Dash, Long> {

	@Query("SELECT d FROM dash d")
    List<Dash> buscarDashboard();

    @Query("SELECT b FROM balancos b where b.id = :identificador")
    List<Balancos> buscarBalancos(Long identificador);
}
