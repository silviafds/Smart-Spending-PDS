package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public Map<String, BigDecimal> saldos() {
        List<Object[]> resultados = saldoRepository.calcularSaldoPorContasHabilitada();
        Map<String, BigDecimal> saldos = new HashMap<>();
        BigDecimal saldoTotal = BigDecimal.ZERO;

        for (Object[] resultado : resultados) {
            String nomeConta = (String) resultado[0];
            Number saldoNumber = (Number) resultado[1];
            BigDecimal saldo = BigDecimal.valueOf(saldoNumber.doubleValue()).setScale(2, RoundingMode.HALF_UP);
            saldos.put(nomeConta, saldo);

            saldoTotal = saldoTotal.add(saldo);
        }


        return saldos;
    }

}
