package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

    @Modifying
    @Query("UPDATE conta_bancaria cb SET cb.nomeBanco = :nome, cb.tipoConta = :tipo, " +
        "cb.agencia = :agencia, cb.numeroConta = :numeroConta WHERE cb.id = :idContaBancaria")
    int editarContaBancaria(
            @Param("idContaBancaria") Long idContaBancaria,
            @Param("nome") String nome,
            @Param("tipo") String tipo,
            @Param("agencia") String agencia,
            @Param("numeroConta") String numeroConta
    );

    @Query("SELECT cb FROM conta_bancaria cb WHERE cb.id = :idContaBancaria")
    List<ContaBancaria> findContaBancariaById(int idContaBancaria);

    @Modifying
    @Transactional
    @Query("DELETE FROM conta_bancaria c WHERE c.id = :id ")
    int deleteContaBancaria(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("SELECT DISTINCT cb.nomeBanco FROM conta_bancaria cb")
    List<String> findContaBancariaByNomeBanco();

    @Modifying
    @Transactional
    @Query("SELECT cb.tipoConta, cb.agencia, cb.numeroConta FROM conta_bancaria cb WHERE cb.nomeBanco = :banco")
    List<String> findContaBancariaByNomeBanco2(@Param("banco") String banco);

}
