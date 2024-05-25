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
    public Map<String, BigDecimal> saldos() {
        //calculaSaldo();
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
    /*public void calculaSaldo() {
        // Obtém as listas de despesas e receitas do repositório
        List<Object[]> despesas = saldoRepository.listaTodasAsDespesas();
        List<Object[]> receitas = saldoRepository.listaTodasAsReceita();

        // Inicializa as variáveis para somar despesas e receitas
        BigDecimal somaDespesas = BigDecimal.ZERO;
        BigDecimal somaReceitas = BigDecimal.ZERO;

        // Itera sobre a lista de despesas e acumula o valor total das despesas
        for (Object[] despesa : despesas) {
            String nomeConta = (String) despesa[0]; // Nome da conta interna
            BigDecimal valorDespesa = (BigDecimal) despesa[1]; // Valor da despesa

            System.out.println("Nome da conta: " + nomeConta + ", Valor da despesa: " + valorDespesa);
            somaDespesas = somaDespesas.add(valorDespesa);
        }

        System.out.println("Soma total das despesas: " + somaDespesas);

        // Itera sobre a lista de receitas e acumula o valor total das receitas
        for (Object[] receita : receitas) {
            String nomeConta = (String) receita[0]; // Nome da conta interna
            BigDecimal valorReceita = (BigDecimal) receita[1]; // Valor da receita

            System.out.println("Nome da conta: " + nomeConta + ", Valor da receita: " + valorReceita);
            somaReceitas = somaReceitas.add(valorReceita);
        }

        System.out.println("Soma total das receitas: " + somaReceitas);

        // Calcula o saldo final subtraindo as despesas das receitas
        BigDecimal saldoFinal = somaReceitas.subtract(somaDespesas);

        System.out.println("Saldo final: " + saldoFinal);
    }*/


    @Override
    public Map<String, BigDecimal>  calculaSaldo() {
        // Obtém as listas de despesas e receitas do repositório
        List<Object[]> despesas = saldoRepository.listaTodasAsDespesas();
        List<Object[]> receitas = saldoRepository.listaTodasAsReceita();

        // Mapas para armazenar a soma das despesas e receitas por conta
        Map<String, BigDecimal> mapaDespesas = new HashMap<>();
        Map<String, BigDecimal> mapaReceitas = new HashMap<>();
        Map<String, BigDecimal> mapaSaldos = new HashMap<>();

        // Itera sobre a lista de despesas e acumula o valor total das despesas por conta
        for (Object[] despesa : despesas) {
            String nomeConta = (String) despesa[0]; // Nome da conta interna
            BigDecimal valorDespesa = (BigDecimal) despesa[1]; // Valor da despesa

            // Soma as despesas por conta
            mapaDespesas.merge(nomeConta, valorDespesa, BigDecimal::add);
        }

        // Itera sobre a lista de receitas e acumula o valor total das receitas por conta
        for (Object[] receita : receitas) {
            String nomeConta = (String) receita[0]; // Nome da conta interna
            BigDecimal valorReceita = (BigDecimal) receita[1]; // Valor da receita

            // Soma as receitas por conta
            mapaReceitas.merge(nomeConta, valorReceita, BigDecimal::add);
        }

        // Calcula o saldo final por conta subtraindo as despesas das receitas
        for (String nomeConta : mapaDespesas.keySet()) {
            BigDecimal totalDespesas = mapaDespesas.getOrDefault(nomeConta, BigDecimal.ZERO);
            BigDecimal totalReceitas = mapaReceitas.getOrDefault(nomeConta, BigDecimal.ZERO);
            BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);

            // Armazena o saldo final no mapa de saldos
            mapaSaldos.put(nomeConta, saldoFinal);
        }

        // Também calcula saldos para contas que só têm receitas
        for (String nomeConta : mapaReceitas.keySet()) {
            if (!mapaDespesas.containsKey(nomeConta)) {
                BigDecimal totalReceitas = mapaReceitas.getOrDefault(nomeConta, BigDecimal.ZERO);
                BigDecimal saldoFinal = totalReceitas;

                // Armazena o saldo final no mapa de saldos
                mapaSaldos.put(nomeConta, saldoFinal);
            }
        }

        // Calcula o saldo total somando todos os saldos finais
        BigDecimal saldoTotal = BigDecimal.ZERO;
        for (BigDecimal saldo : mapaSaldos.values()) {
            saldoTotal = saldoTotal.add(saldo);
        }

        // Armazena o saldo total no mapa de saldos
        mapaSaldos.put("Saldo total", saldoTotal);

        // Retorna o mapa de saldos
        //return mapaSaldos;

        Map<String, BigDecimal> mapaSaldosReverso = mapaSaldos.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Retorna o mapa de saldos revertido
        return mapaSaldosReverso;
    }













    /*public void calculaSaldo() {
        List<Object[]> resultados = saldoRepository.listaTodasAsDespesas();
        List<Object[]> receitas = saldoRepository.listaTodasAsReceita();
        BigDecimal somaDespesas = BigDecimal.ZERO;

        for (Object[] resultado : resultados) {
            String nomeConta = (String) resultado[0]; // Nome da conta interna
            BigDecimal valorDespesa = (BigDecimal) resultado[1]; // Valor da despesa

            System.out.println("Nome da conta: " + nomeConta + ", Valor da despesa: " + valorDespesa);
            somaDespesas = somaDespesas.add(valorDespesa);
        }
        System.out.println("Soma total das despesas: " + somaDespesas);

        for (Object[] resultado : receitas) {
            String nomeConta = (String) resultado[0]; // Nome da conta interna
            BigDecimal valorDespesa = (BigDecimal) resultado[1]; // Valor da despesa

            System.out.println("Nome da conta: " + nomeConta + ", Valor da receita: " + valorDespesa);

        }

    }*/

}