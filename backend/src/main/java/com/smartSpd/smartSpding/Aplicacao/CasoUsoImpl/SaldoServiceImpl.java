package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.SaldoService;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
public class SaldoServiceImpl implements SaldoService {

    private final SaldoRepository saldoRepository;

    @Autowired
    public SaldoServiceImpl(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }


    @Override
    public Map<String, BigDecimal>  calculaSaldo() {
        List<Object[]> despesas = saldoRepository.listaTodasAsDespesas();
        List<Object[]> receitas = saldoRepository.listaTodasAsReceita();

        Map<String, BigDecimal> mapaDespesas = new HashMap<>();
        Map<String, BigDecimal> mapaReceitas = new HashMap<>();
        Map<String, BigDecimal> mapaSaldos = new HashMap<>();

        for (Object[] despesa : despesas) {
            String nomeConta = (String) despesa[0];
            BigDecimal valorDespesa = (BigDecimal) despesa[1];

            mapaDespesas.merge(nomeConta, valorDespesa, BigDecimal::add);
        }

        for (Object[] receita : receitas) {
            String nomeConta = (String) receita[0];
            BigDecimal valorReceita = (BigDecimal) receita[1];

            mapaReceitas.merge(nomeConta, valorReceita, BigDecimal::add);
        }

        for (String nomeConta : mapaDespesas.keySet()) {
            BigDecimal totalDespesas = mapaDespesas.getOrDefault(nomeConta, BigDecimal.ZERO);
            BigDecimal totalReceitas = mapaReceitas.getOrDefault(nomeConta, BigDecimal.ZERO);
            BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);

            mapaSaldos.put(nomeConta, saldoFinal);
        }

        for (String nomeConta : mapaReceitas.keySet()) {
            if (!mapaDespesas.containsKey(nomeConta)) {
                BigDecimal totalReceitas = mapaReceitas.getOrDefault(nomeConta, BigDecimal.ZERO);
                BigDecimal saldoFinal = totalReceitas;

                mapaSaldos.put(nomeConta, saldoFinal);
            }
        }

        BigDecimal saldoTotal = BigDecimal.ZERO;
        for (BigDecimal saldo : mapaSaldos.values()) {
            saldoTotal = saldoTotal.add(saldo);
        }

        mapaSaldos.put("Saldo total", saldoTotal);

        Map<String, BigDecimal> mapaSaldosReverso = mapaSaldos.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return mapaSaldosReverso;
    }

}