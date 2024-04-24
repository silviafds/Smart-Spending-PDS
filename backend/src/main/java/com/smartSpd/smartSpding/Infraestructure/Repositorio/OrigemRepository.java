package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrigemRepository extends JpaRepository<Origem, Long> {

    @Query("SELECT f FROM origem f")
    List<Origem> findByAllOrigem();

}
