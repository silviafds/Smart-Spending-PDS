package com.smartSpd.smartSpding.Core.Strategy;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilDespesa;

public class HospitalBalancoStrategy implements BalancoStrategy {

    @Override
    public void criarBalanco(Balancos balancos) {

        CategoriaDespesa manutencaoMaquinario = criarCategoria("Manutenção de Maquinário");
        CategoriaDespesa manutencaoLeitos = criarCategoria("Manutenção dos Leitos de UTI");
        CategoriaDespesa materialDescartavel = criarCategoria("Material Descartável");

        TituloContabilDespesa reparosMaquinario = criarTituloContabil("Reparos de Equipamentos", manutencaoMaquinario);
        TituloContabilDespesa reparosLeito = criarTituloContabil("Reparos de Leitos", manutencaoLeitos);
        TituloContabilDespesa compraMaterial = criarTituloContabil("Compra de Material Descartável", materialDescartavel);

        balancos.adicionaCategoria(manutencaoMaquinario);
        balancos.adicionaCategoria(manutencaoLeitos);
        balancos.adicionaCategoria(materialDescartavel);

        balancos.adicionaTituloContabil(reparosMaquinario);
        balancos.adicionaTituloContabil(reparosLeito);
        balancos.adicionaTituloContabil(compraMaterial);

    }

    @Override
    public CategoriaDespesa criarCategoria(String nome) {
        return new CategoriaDespesa(nome, "hospital");
    }

    @Override
    public TituloContabilDespesa criarTituloContabil(String nome, CategoriaDespesa categoria) {
        return new TituloContabilDespesa(nome, categoria);
    }
}
