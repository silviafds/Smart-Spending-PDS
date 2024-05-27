package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.CHEQUE;
import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.PAPEL_E_MOEDA;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

     default int editarDespesa(DespesaDTO despesaDTO, String[] dadosReformulados) throws Exception {
        int rowsUpdated = 0;

         try {
             Despesa despesa = findById(despesaDTO.getId()).orElse(null);

             if (despesa != null) {
                 updateDespesaFields(despesa, despesaDTO, dadosReformulados);
                 save(despesa);
                 rowsUpdated = 1;
             }
         } catch (NullPointerException e) {
             e.printStackTrace();
             throw new Exception("Ocorreu um erro de NullPointerException durante a edição da despesa.");
         }

        return rowsUpdated;
    }

    private void updateDespesaFields(Despesa despesa, DespesaDTO despesaDTO, String[] dadosReformulados) {
        BigDecimal big = formataMoeda(despesaDTO.getValorProjeto());

        despesa.setCategoria(despesaDTO.getCategoria());
        despesa.setTitulo_contabil(despesaDTO.getTitulo_contabil());
        despesa.setDataDespesa(despesaDTO.getDataDespesa());
        despesa.setValorDespesa(big);
        despesa.setCategoriaTransacao(despesaDTO.getCategoriaTransacao());

        if (despesaDTO.getCategoriaTransacao().equals(CHEQUE.getMeiosPagamento()) ||
                despesaDTO.getCategoriaTransacao().equals(PAPEL_E_MOEDA.getMeiosPagamento())) {
            despesa.setTipoContaOrigem("");
            despesa.setBancoOrigem("");
            despesa.setAgenciaOrigem("");
            despesa.setNumeroContaOrigem("");
        } else {
            despesa.setTipoContaOrigem(dadosReformulados[0]);
            despesa.setBancoOrigem(despesa.getBancoOrigem());
            despesa.setAgenciaOrigem(dadosReformulados[1]);
            despesa.setNumeroContaOrigem(dadosReformulados[2]);
        }

        despesa.setBeneficiario(despesaDTO.getBeneficiario());
        despesa.setBancoDestino(despesaDTO.getBancoDestino());
        despesa.setAgenciaDestino(despesaDTO.getAgenciaDestino());
        despesa.setNumeroContaDestino(despesaDTO.getNumeroContaDestino());
        despesa.setDescricao(despesaDTO.getDescricao());
        despesa.setContaInterna(despesaDTO.getContaInterna());
    }

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa(c.id, c.nome) FROM categoria_despesa c")
    List<CategoriaDespesa> buscarTodasAsCategoriaDespesa();

    @Query("SELECT d FROM despesa d")
    List<Despesa> buscarTodasDespesas();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.TituloContabilDespesa(tcd.id, tcd.nome) FROM titulos_contabeis_despesa tcd WHERE tcd.categoriaDespesa.id = :idCategoria")
    List<TituloContabilDespesa> findByAllTitulosContabeisDespesa(int idCategoria);

    @Query("SELECT d FROM despesa d WHERE d.id = :idDespesa")
    List<Despesa> buscarDespesaPorId(int idDespesa);


    @Query("SELECT SUM(d.valorDespesa) FROM despesa d WHERE d.dataDespesa BETWEEN :dataInicio AND :dataTermino")
    double totalDespesaPorPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                  @Param("dataTermino") LocalDate dataTermino);

    @Query(nativeQuery = true,
            value = "SELECT d.categoria, SUM(valor_despesa) FROM despesa d " +
                    "WHERE data_despesa BETWEEN :startDate AND :endDate " +
                    "GROUP BY categoria " +
                    "ORDER BY SUM(valor_despesa) DESC " +
                    "LIMIT 3")
    List<Object[]> encontrarDespesasPorCategoria(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT cd.nome from categoria_despesa cd where cd.id = :id")
    String projetosPorCategoria(int id);


    default BigDecimal formataMoeda(String numero) {
        String stringNumerica = numero.replaceAll("[^0-9,]", "");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);

        BigDecimal numeros;
        try {
            Number parsedNumber = decimalFormat.parse(stringNumerica);

            numeros = new BigDecimal(parsedNumber.toString());

            numeros = numeros.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            System.out.println("Erro ao converter número: " + e.getMessage());
            return null;
        }

        return numeros;
    }

}