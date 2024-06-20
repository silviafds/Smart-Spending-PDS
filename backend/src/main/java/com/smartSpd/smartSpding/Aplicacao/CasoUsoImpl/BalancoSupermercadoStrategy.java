package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;

import java.util.List;

public class BalancoSupermercadoStrategy implements BalancoStrategy {
    @Override
    public List<BalancoDespesaReceita> obterDadosBalanco(BalancoRapidoDTO balancoRapidoDTO) {
        System.out.println("estrategia de supermercado");
        return null;
    }
}
