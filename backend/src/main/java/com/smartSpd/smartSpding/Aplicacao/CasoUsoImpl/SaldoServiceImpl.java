package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;


import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;

import java.util.List;

public class SaldoServiceImpl implements SaldoService {

    private final SaldoRepository saldoRepository;

    public SaldoServiceImpl(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }

    @Override
    public Double calcularSaldoPorContaInterna(Long idContaInterna) {
        return saldoRepository.calcularSaldoPorContaInterna(idContaInterna);
    }
}
