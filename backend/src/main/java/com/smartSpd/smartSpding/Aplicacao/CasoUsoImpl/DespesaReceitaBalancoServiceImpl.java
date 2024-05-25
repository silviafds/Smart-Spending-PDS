package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorDespesaReceita;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DespesaReceitaBalancoServiceImpl implements DespesaReceitaBalancoService {

    @Autowired
    private GerenciadorDespesaReceita gerenciadorDespesaReceita;

    public DespesaReceitaBalancoServiceImpl(GerenciadorDespesaReceita gerenciadorDespesaReceita) {
        this.gerenciadorDespesaReceita = gerenciadorDespesaReceita;
    }

    @Override
    public List<BalancoDespesaReceita> buscarDadosReceitaDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        String sql;
        List<BalancoDespesaReceita> resultados = new ArrayList<>();

        gerenciadorDespesaReceita.verificaDTOIsNULL(balancoRapidoDTO);

        sql = gerenciadorDespesaReceita.queryBalanco(balancoRapidoDTO);
        resultados.addAll(gerenciadorDespesaReceita.montaQuery(sql, balancoRapidoDTO));

        return resultados;
    }

}