package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HospitalBalancosStrategyImpl implements BalancoStrategy {

    private static final double IMPOSTO_HOSPITAL = 0.05;

    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    public HospitalBalancosStrategyImpl(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @Override
    public double calcularDespesaComImposto(double despesa) {
        return despesa + (despesa * IMPOSTO_HOSPITAL);
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        List<BalancoDespesaReceita> dadosDespesa = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        List<BalancoDespesaReceita> balancos = new ArrayList<>();

        for (BalancoDespesaReceita balanco : dadosDespesa) {
            double despesaComImposto = calcularDespesaComImposto(balanco.getValor());
            BalancoDespesaReceita novoBalanco = new BalancoDespesaReceita(balanco.getDado(), Math.round(despesaComImposto), balanco.getCategoriaOuTituloContabil());
            balancos.add(novoBalanco);
        }

        return balancos;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO) {
        return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
    }

}