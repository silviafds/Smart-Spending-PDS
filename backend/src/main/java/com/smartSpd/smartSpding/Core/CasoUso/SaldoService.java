package com.smartSpd.smartSpding.Core.CasoUso;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface SaldoService {

    Map<String, BigDecimal> saldos();

}
