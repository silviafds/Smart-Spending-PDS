package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SupermercadoBalancosStrategyImpl implements BalancoStrategy {

    private static final double IMPOSTO_SUPERMERCADO = 0.08;

    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    public SupermercadoBalancosStrategyImpl(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @Override
    public double calcularDespesaComImposto(double despesa) {
        return despesa + (despesa * IMPOSTO_SUPERMERCADO);
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        List<BalancoDespesaReceita> dadosReceitaDespesa = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        List<BalancoDespesaReceita> balancos = new ArrayList<>();

        for (BalancoDespesaReceita balanco : dadosReceitaDespesa) {
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
