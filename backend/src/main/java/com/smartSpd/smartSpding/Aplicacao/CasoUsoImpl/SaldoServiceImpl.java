package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Core.Dominio.Despesa;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;

import java.util.List;

public class SaldoServiceImpl implements SaldoService {

    private final SaldoRepository saldoRepository;
    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public SaldoServiceImpl(SaldoRepository saldoRepository, ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.saldoRepository = saldoRepository;
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    @Override
    public Double calcularSaldoPorContaInterna(Long idContaInterna) {
        Double saldoTotal = 0.0;

        List<Receita> receitas = receitaRepository.findByIdContaInterna(idContaInterna);
        for (Receita receita : receitas) {
            saldoTotal += receita.getValorReceita();
        }

        List<Despesa> despesas = despesaRepository.findByIdContaInterna(idContaInterna);
        for (Despesa despesa : despesas) {
            saldoTotal -= despesa.getValorDespesa();
        }

        return saldoTotal;
    }
}
