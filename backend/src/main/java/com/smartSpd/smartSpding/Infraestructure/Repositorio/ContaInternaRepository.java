package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContaInternaRepository extends JpaRepository<ContaInterna, Long> {

    @Query("SELECT c FROM ContaInterna c WHERE c.nome = :name")
    ContaInterna findByNome(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE ContaInterna p SET p.nome = :novo, p.desabilitarConta = :desabilitarConta WHERE p.id = :id")
    int atualizarNomeContaInterna(int id, String novo, Boolean desabilitarConta);

    @Modifying
    @Transactional
    @Query("UPDATE ContaInterna p SET p.desabilitarConta = :desabilitarconta WHERE p.id = :id")
    int atualizarStatusContaInterna(int id, Boolean desabilitarconta);

    @Modifying
    @Transactional
    @Query("DELETE FROM ContaInterna c WHERE c.id = :idConta " +
            "AND NOT EXISTS (SELECT 1 FROM receita r WHERE r.contaInterna.id = :idConta)")
    int deletarContaInterna(@Param("idConta") int idConta);

    @Query("SELECT c.id FROM ContaInterna c WHERE c.nome = :name")
    Long findByNomeReturnId(@Param("name") String name);

    @Query("SELECT c FROM ContaInterna c")
    List<ContaInterna> findByAllContaInterna();

    @Query("SELECT c FROM ContaInterna c WHERE c.desabilitarConta = false or c.desabilitarConta IS NULL")
    List<ContaInterna> findByAllContaInternaHabilitadas();

    @Query("SELECT c FROM ContaInterna c WHERE c.id = :idConta")
    ContaInterna findByIdContaInterna(int idConta);
}
