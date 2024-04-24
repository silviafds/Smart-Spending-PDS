package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConselhosRepository extends JpaRepository<Conselhos, Long> {

    default int salvarConselhos(ConselhosDTO data) throws Exception {
        int rowsUpdated = 0;
        try {
            Conselhos conselhos = new Conselhos(data.identificador(), data.statusDespesa(), data.metaDespesa(),
                    data.statusReceita(), data.metaReceita(), data.tempoConselho());

            if (conselhos != null) {
                save(conselhos);
                rowsUpdated = 1;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("Ocorreu um erro de NullPointerException durante a edição do conselho.");
        }
        return rowsUpdated;
    }

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