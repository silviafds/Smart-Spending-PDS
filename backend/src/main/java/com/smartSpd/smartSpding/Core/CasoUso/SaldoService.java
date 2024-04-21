package com.smartSpd.smartSpding.Core.CasoUso;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface SaldoService {

    Map<Long, Double> calcularSaldoPorContaHabilitada();

    Double calcularSaldoTotal();

}
