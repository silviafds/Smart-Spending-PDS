package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;

import java.util.List;

public interface BalancoStrategy {

    double calcularDespesaComImposto(double despesa);
    List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO);
    List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO);
}
