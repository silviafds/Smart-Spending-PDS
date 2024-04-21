package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaldoServiceImpl implements SaldoService {

    private final SaldoRepository saldoRepository;

    @Autowired
    public SaldoServiceImpl(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }

    @Override
    public Map<Long, Double> calcularSaldoPorContaHabilitada() {
        List<Object[]> idsComSaldo = saldoRepository.calcularSaldoPorContaHabilitada();
        Map<Long, Double> saldoPorContaInterna = new HashMap<>();

        for (Object[] registro : idsComSaldo) {
            Long idContaInterna = (Long) registro[0];
            Double saldoConta = (Double) registro[1];
            saldoPorContaInterna.put(idContaInterna, saldoConta);
        }

        return saldoPorContaInterna;
    }

    @Override
    public Double calcularSaldoTotal() {
        Map<Long, Double> saldoPorContaInterna = calcularSaldoPorContaHabilitada();
        Double saldoTotal = 0.0;

        for (Double saldoPorConta : saldoPorContaInterna.values()) {
            saldoTotal += saldoPorConta;
        }

        return saldoTotal;
    }

}
