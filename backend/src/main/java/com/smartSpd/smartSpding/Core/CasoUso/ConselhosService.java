package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ConselhosService {

    Boolean salvarConselhos(ConselhosDTO data);

    List<Conselhos> buscarConfiguracaoConselhos();

    List<String> conselhosPorBalanco(BalancoRapidoDTO balancoRapidoDTO);

    /*    BALANÇO: AMBOS
    * Análise no qual verifica quando a despesa é maior que receita
    * ou quando a receita é maior que despesa  ou quando são iguais
    * */
    String avisarSobreExcessoDespesa(BalancoRapidoDTO balancoRapidoDTO);

    /*    BALANÇO: AMBOS
     * Análise no qual verifica quando a despesa é maior que receita
     * ou quando a receita é maior que despesa  ou quando são iguais
     * */
    String avisarSobreExcessoReceita(BalancoRapidoDTO balancoRapidoDTO);

    /*    BALANÇO: AMBOS
     * Análise no qual verifica quando a despesa é maior que receita
     * ou quando a receita é maior que despesa  ou quando são iguais
     * */
    String avisarSobreIgualdadeReceitaDespesa(BalancoRapidoDTO balancoRapidoDTO);

    /*  BALANÇO: AMBOS E DESPESA
     * Análise no qual verifica quando a despesa do mes atual é maior
     * ou menor que o mes anterior
     * */
    String compararDespesaMesAtualEAnterior(BalancoRapidoDTO balancoRapidoDTO);

    /* BALANÇO: DESPESA
     * Análise no qual verifica quando a despesa do mes atual é maior
     * ou menor que o mes anterior
     * */
    String compararReceitaMesAtualEAnterior(BalancoRapidoDTO balancoRapidoDTO);

    /*  BALANÇO: DESPESA
     * Análise no qual verifica quando a meta de despesa é atinginda
     * */
    String gerarConselhoMetaDespesa(LocalDate dataInicio, LocalDate dataFinal);

    /* BALANÇO: RECEITA
     * Análise no qual verifica quando a meta de receita é atinginda
     * */
    String gerarConselhoMetaReceita(LocalDate dataInicio, LocalDate dataFinal);

    /* BALANÇO: AMBOS E DESPESA
     * Análise no qual verifica qual despesa ele podera reduzir
     * */
    String despesaAReduzir(LocalDate dataInicio, LocalDate dataFinal);

    /* BALANÇO: AMBOS E DESPESA
     * Análise no qual verifica qual receita ele podera aumentar
     * */
    String receitasAAumentar(LocalDate dataInicio, LocalDate dataFinal);
}