package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Infraestructure.Repositorio.ConselhosRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class GerenciadorConselhos {

    private final ConselhosRepository conselhosRepository;

    public GerenciadorConselhos(ConselhosRepository conselhosRepository) {
        this.conselhosRepository = conselhosRepository;
    }

    public String buscarData(LocalDate dataInicio) {
        int ano = dataInicio.getYear();
        int mes = dataInicio.getMonthValue();
        System.out.println("mes: "+mes);

        YearMonth yearMonth = YearMonth.of(ano, mes-1);
        int quantidadeDiasMesAnterior = yearMonth.lengthOfMonth();

        return String.valueOf(quantidadeDiasMesAnterior);
    }


    public List<LocalDate> montarDataQuery(LocalDate dataInicio, String quantidadeDeMesAnterior) {
        int ano = dataInicio.getYear();
        int mes = dataInicio.getMonthValue();
        List<LocalDate> datasQuery = new ArrayList<>();

        LocalDate dataInicial = LocalDate.of(ano, mes-1, 1);
        LocalDate dataFinal = LocalDate.of(ano, mes-1, Integer.parseInt(quantidadeDeMesAnterior));
        datasQuery.add(dataInicial);
        datasQuery.add(dataFinal);

        return datasQuery;
    }

    public String formataData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dataFormatada = data.format(formatter);

        return dataFormatada;
    }

    public boolean verificacaoMetaDespesa() {
        return conselhosRepository.verificaDefinicaoMetaDespesa();
    }

    public boolean verificacaoMetaReceita() {
        return conselhosRepository.verificaDefinicaoMetaReceita();
    }



}
