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
        List<Long> idsContaInterna = saldoRepository.findAllIdsContaInterna();
        Map<Long, Double> saldoPorContaInterna = new HashMap<>();

        for (Long idContaInterna : idsContaInterna) {
            Double saldoConta = saldoRepository.calcularSaldoPorContaHabilitada();
            saldoPorContaInterna.put(idContaInterna, saldoConta);
        }

        return saldoPorContaInterna;
    }

}
