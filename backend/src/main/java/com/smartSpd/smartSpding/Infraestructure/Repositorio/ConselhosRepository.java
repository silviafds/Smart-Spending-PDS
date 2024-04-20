package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConselhosRepository extends JpaRepository<Conselhos, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE conselhos c SET c.status_despesa = :statusDespesa, c.meta_despesa = :metaDespesa," +
            "c.status_receita = :statusReceita, c.meta_receita = :metaReceita, c.tempo_conselho = :tempoConselho" +
            " WHERE c.identificador = :id")
    int salvarConselhos(@Param("id") int id,
                        @Param("statusDespesa") boolean statusDespesa,
                        @Param("metaDespesa") String metaDespesa,
                        @Param("statusReceita") boolean statusReceita,
                        @Param("metaReceita") String metaReceita,
                        @Param("tempoConselho") String tempoConselho);


    @Query("SELECT c FROM conselhos c")
    List<Conselhos> buscarConselhos();
}