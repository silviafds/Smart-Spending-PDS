package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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


    @Query("SELECT " +
            "(SELECT SUM(d.valorDespesa) FROM despesa d WHERE d.dataDespesa BETWEEN :startDate AND :endDate) AS totalDespesa, " +
            "(SELECT SUM(r.valorReceita) FROM receita r WHERE r.dataReceita BETWEEN :startDate AND :endDate) AS totalReceita ")
    List<Object[]> calcularTotalPorPeriodo(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);



    @Query("SELECT " +
            "(SELECT SUM(d.valorDespesa) FROM despesa d WHERE d.dataDespesa BETWEEN :dataInicio AND :dataTermino) AS totalDespesa, " +
            "(SELECT SUM(d.valorDespesa) FROM despesa d WHERE d.dataDespesa BETWEEN :dataInicialMesAnterior AND :dataFinalMesAnterior) AS totalDespesas ")
    List<Object[]> avisoDespesaSuperiorMesAnteriorCategoriaMaiorGasto(@Param("dataInicio") LocalDate dataInicio,
                                                                      @Param("dataTermino") LocalDate dataTermino,
                                                                      @Param("dataInicialMesAnterior") LocalDate dataInicialMesAnterior,
                                                                      @Param("dataFinalMesAnterior") LocalDate dataFinalMesAnterior);

    @Query("SELECT " +
            "(SELECT SUM(r.valorReceita) FROM receita r WHERE r.dataReceita BETWEEN :dataInicio AND :dataTermino) AS totalReceita, " +
            "(SELECT SUM(r.valorReceita) FROM receita r WHERE r.dataReceita BETWEEN :dataInicialMesAnterior AND :dataFinalMesAnterior) AS totalReceitaMesAnterior ")
    List<Object[]> avisoReceitaSuperiorMesAnteriorCategoriaMaiorGasto(@Param("dataInicio") LocalDate dataInicio,
                                                                      @Param("dataTermino") LocalDate dataTermino,
                                                                      @Param("dataInicialMesAnterior") LocalDate dataInicialMesAnterior,
                                                                      @Param("dataFinalMesAnterior") LocalDate dataFinalMesAnterior);

    @Query("Select c.status_despesa from conselhos c where c.status_despesa = true")
    boolean verificaDefinicaoMetaDespesa();

    @Query("Select c.meta_despesa from conselhos c where c.status_despesa = true")
    String metaDefinidaDespesa();

    @Query("Select c.status_receita from conselhos c where c.status_receita = true")
    boolean verificaDefinicaoMetaReceita();

    @Query("Select c.meta_receita from conselhos c where c.status_receita = true")
    String metaDefinidaReceita();
}