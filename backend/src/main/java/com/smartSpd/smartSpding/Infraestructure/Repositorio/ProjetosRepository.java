package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Projetos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjetosRepository extends JpaRepository<Projetos, Long> {

    @Query("SELECT p FROM projetos p")
    List<Projetos> buscarTodosProjetos();

    @Query("SELECT p FROM projetos p where p.id = :id")
    List<Projetos> buscarProjetoPorID(long id);

}