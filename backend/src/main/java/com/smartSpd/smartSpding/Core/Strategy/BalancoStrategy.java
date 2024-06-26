package com.smartSpd.smartSpding.Core.Strategy;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilDespesa;

public interface BalancoStrategy {
    void criarBalanco(Balancos balanco);
    void criarCategoria(String nomeCategoria, double valorGasto, double valorInvestimento, Balancos balancos);
    CategoriaDespesa criarCategoria(String nome);
    TituloContabilDespesa criarTituloContabil(String nome, CategoriaDespesa categoria);
    void editarBalanco(Balancos balancos);
}
