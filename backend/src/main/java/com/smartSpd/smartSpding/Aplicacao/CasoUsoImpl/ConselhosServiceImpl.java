package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorConselhos;
import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import com.smartSpd.smartSpding.Core.Strategy.ConselhosStrategy;
import com.smartSpd.smartSpding.Core.Strategy.HospitalConselhosStrategy;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ConselhosRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import com.smartSpd.smartSpding.Core.Strategy.ConselhosStrategy;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ConselhosServiceImpl implements ConselhosService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ConselhosRepository conselhosRepository;

    private final GerenciadorConselhos gerenciadorConselhos;

    private final DespesaRepository despesaRepository;

    private final ReceitaRepository receitaRepository;

    private final ConselhosStrategy conselhosStrategy;

    public ConselhosServiceImpl(ConselhosRepository conselhosRepository, GerenciadorConselhos gerenciadorConselhos,
                                DespesaRepository despesaRepository, ReceitaRepository receitaRepository, HospitalConselhosStrategy hospitalConselhosStrategy) {
        this.conselhosRepository = conselhosRepository;
        this.gerenciadorConselhos = gerenciadorConselhos;
        this.despesaRepository = despesaRepository;
        this.receitaRepository = receitaRepository;
        this.conselhosStrategy = hospitalConselhosStrategy;
    }

    @Override
    public Boolean salvarConselhos(ConselhosDTO data) {
        try {
            if (data.identificador() == 1) {
                ConselhosDTO dataStrategy = conselhosStrategy.gerarConselho(data);
                conselhosRepository.salvarConselhos(dataStrategy);
            }
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar dados de conselhos. ", e);
            return false;
        }
    }

    @Override
    public List<Conselhos> buscarConfiguracaoConselhos() {
        return conselhosRepository.buscarConselhos();
    }

    @Override
    public List<String> conselhosPorBalanco(BalancoRapidoDTO balancoRapidoDTO) {
        List<String> conselhos = new ArrayList<>();

        switch (balancoRapidoDTO.getTipoBalanco()) {
            case "Despesa e Receita":
                adicionarExcessoDespesa(balancoRapidoDTO, conselhos);
                adicionarExcessoReceita(balancoRapidoDTO, conselhos);
                conselhos.add(compararDespesaMesAtualEAnterior(balancoRapidoDTO));
                conselhos.add(compararReceitaMesAtualEAnterior(balancoRapidoDTO));
                conselhos.add(receitasAAumentar(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino()));
                conselhos.add(despesaAReduzir(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino()));
                break;
            case "Despesa":
                adicionarExcessoDespesa(balancoRapidoDTO, conselhos);
                conselhos.add(compararDespesaMesAtualEAnterior(balancoRapidoDTO));
                conselhos.add(despesaAReduzir(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino()));
                break;
            case "Receita":
                adicionarExcessoReceita(balancoRapidoDTO, conselhos);
                conselhos.add(compararReceitaMesAtualEAnterior(balancoRapidoDTO));
                conselhos.add(receitasAAumentar(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino()));
                break;
        }

        adicionarIgualdadeReceitaDespesa(conselhos, balancoRapidoDTO);
        adicionarMetaDespesa(balancoRapidoDTO, conselhos);
        adicionarMetaReceita(balancoRapidoDTO, conselhos);

        return conselhos;
    }

    private void adicionarExcessoDespesa(BalancoRapidoDTO balancoRapidoDTO, List<String> conselhos) {
        String excessoDespesa = avisarSobreExcessoDespesa(balancoRapidoDTO);
        if (excessoDespesa != null) {
            conselhos.add(excessoDespesa);
        }
    }

    private void adicionarExcessoReceita(BalancoRapidoDTO balancoRapidoDTO, List<String> conselhos) {
        String excessoReceita = avisarSobreExcessoReceita(balancoRapidoDTO);
        if (excessoReceita != null) {
            conselhos.add(excessoReceita);
        }
    }

    private void adicionarIgualdadeReceitaDespesa(List<String> conselhos, BalancoRapidoDTO balancoRapidoDTO) {
        String igualdadeReceitaDespesa = avisarSobreIgualdadeReceitaDespesa(balancoRapidoDTO);
        if (igualdadeReceitaDespesa != null) {
            conselhos.add(igualdadeReceitaDespesa);
        }
    }

    private void adicionarMetaDespesa(BalancoRapidoDTO balancoRapidoDTO, List<String> conselhos) {
        if (gerenciadorConselhos.verificacaoMetaDespesa()) {
            String conselhoMeta = gerarConselhoMetaDespesa(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
            conselhos.add(conselhoMeta);
        }
    }

    private void adicionarMetaReceita(BalancoRapidoDTO balancoRapidoDTO, List<String> conselhos) {
        if (gerenciadorConselhos.verificacaoMetaReceita()) {
            String conselhoMeta = gerarConselhoMetaReceita(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
            conselhos.add(conselhoMeta);
        }
    }

    @Override
    public String avisarSobreExcessoDespesa(BalancoRapidoDTO dado) {
        List<Object[]> valoresBalanco = conselhosRepository.calcularTotalPorPeriodo(dado.getDataInicio(), dado.getDataTermino());

        Object[] balanco = valoresBalanco.get(0);

        BigDecimal totalDespesa = (BigDecimal) balanco[0];
        BigDecimal totalReceita = (BigDecimal) balanco[1];
        String conselho = null;

        if(totalReceita != null) {
            int comparacao = totalDespesa.compareTo(totalReceita);
            if (comparacao > 0) {
                BigDecimal diferenca = totalDespesa.subtract(totalReceita);
                conselho = "O montante de despesa superou o de receita em R$" + diferenca + " Sugere-se revisar o " +
                        "orçamento para manter um equilíbrio financeiro adequado.";
            }
        } else {
            conselho = "Não houve receita até o momento para calcular.";
        }

        return conselho;
    }

    @Override
    public String avisarSobreExcessoReceita(BalancoRapidoDTO dado) {
        List<Object[]> valoresBalanco = conselhosRepository.calcularTotalPorPeriodo(dado.getDataInicio(), dado.getDataTermino());

        Object[] balanco = valoresBalanco.get(0);

        BigDecimal totalDespesa = (BigDecimal) balanco[0];
        BigDecimal totalReceita = (BigDecimal) balanco[1];
        String conselho = null;

        if (totalReceita != null) {
            if (totalReceita.compareTo(totalDespesa) > 0) {
                BigDecimal diferenca = totalReceita.subtract(totalDespesa);
                conselho = "O montante de receita superou o de despesa em R$" + diferenca + ". Isso é ótimo, continuando dessa forma seu patrimônio irá crescer.";
            }
        } else {
            conselho = "No momento, o montante de receita ainda não superou o de despesa.";
        }

        return conselho;
    }


    @Override
    public String avisarSobreIgualdadeReceitaDespesa(BalancoRapidoDTO dado) {
        List<Object[]> valoresBalanco = conselhosRepository.calcularTotalPorPeriodo(dado.getDataInicio(), dado.getDataTermino());

        Object[] balanco = valoresBalanco.get(0);

        BigDecimal totalDespesa = (BigDecimal) balanco[0];
        BigDecimal totalReceita = (BigDecimal) balanco[1];
        String conselho = null;

        if (Objects.equals(totalReceita, totalDespesa)) {
            conselho = "Observa-se que o montante das despesas equivale ao da receita em R$"+totalDespesa +
                    ". Recomenda-se uma análise minuciosa das receitas para garantir um equilíbrio financeiro saudável.";
        }

        return conselho;
    }

    @Override
    public String compararDespesaMesAtualEAnterior(BalancoRapidoDTO dado) {
        String conselho = null;
        String quantidadeDeMesAnterior = gerenciadorConselhos.buscarData(dado.getDataInicio());

        List<LocalDate> listaDataMesAnterior = gerenciadorConselhos.montarDataQuery(dado.getDataInicio(), quantidadeDeMesAnterior);

        List<Object[]> teste = conselhosRepository.avisoDespesaSuperiorMesAnteriorCategoriaMaiorGasto(dado.getDataInicio(),
                dado.getDataTermino(), listaDataMesAnterior.get(0), listaDataMesAnterior.get(1));

        Object[] balanco = teste.get(0);

        BigDecimal despesaMesAtual = (BigDecimal) balanco[0];
        BigDecimal despesaMesAnterior = (BigDecimal) balanco[1];

        if(despesaMesAnterior == null) {
            conselho = "Não houve despesas no mês anterior para comprar com o mês atual.";
        } else {
            int comparacao = despesaMesAtual.compareTo(despesaMesAnterior);
            if (comparacao > 0) {
                BigDecimal diferenca = despesaMesAtual.subtract(despesaMesAnterior);
                conselho = "Atenção: Sua despesa atual ultrapassou a despesa do mês anterior em R$" + diferenca;
            } else if (comparacao < 0) {
                BigDecimal diferenca = despesaMesAnterior.subtract(despesaMesAtual);
                conselho = "Parabéns! Sua despesa no período de " + gerenciadorConselhos.formataData(dado.getDataInicio())
                        + " a "+ gerenciadorConselhos.formataData(dado.getDataTermino()) +" permanece inferior à do mês" +
                        " anterior, com uma diferença de R$" + diferenca.setScale(2);
            } else {
                conselho = "Seus gastos atuais permanecem iguais aos do mês anterior, totalizando R$" + despesaMesAtual.setScale(2) +
                        " É recomendável analisar os detalhes de seus gastos para manter uma gestão financeira equilibrada.";
            }
        }

        return conselho;
    }
    @Override
    public String compararReceitaMesAtualEAnterior(BalancoRapidoDTO dado) {
        String quantidadeDeMesAnterior = gerenciadorConselhos.buscarData(dado.getDataInicio());

        List<LocalDate> listaDataMesAnterior = gerenciadorConselhos.montarDataQuery(dado.getDataInicio(), quantidadeDeMesAnterior);

        List<Object[]> teste = conselhosRepository.avisoReceitaSuperiorMesAnteriorCategoriaMaiorGasto(dado.getDataInicio(),
                dado.getDataTermino(), listaDataMesAnterior.get(0), listaDataMesAnterior.get(1));

        Object[] balanco = teste.get(0);

        BigDecimal despesaMesAtual = (BigDecimal) balanco[0];
        BigDecimal despesaMesAnterior = (BigDecimal) balanco[1];

        String conselho = null;

        if (despesaMesAnterior == null || despesaMesAtual == null) {
            conselho = "Não houve receita no mês anterior para comparar com o mês atual.";
        } else {
            int comparacao = despesaMesAtual.compareTo(despesaMesAnterior);
            if (comparacao > 0) {
                BigDecimal diferenca = despesaMesAtual.subtract(despesaMesAnterior);
                conselho = "Parabéns: Sua receita atual ultrapassou a receita do mês anterior em R$" + diferenca;
            } else if (comparacao < 0) {
                BigDecimal diferenca = despesaMesAnterior.subtract(despesaMesAtual);
                conselho = "Atenção! Sua receita no período de " + gerenciadorConselhos.formataData(dado.getDataInicio())
                        + " a "+ gerenciadorConselhos.formataData(dado.getDataTermino()) +" permanece inferior à do mês anterior, com uma diferença de R$" + diferenca;
            } else {
                conselho = "Suas receitas atuais permanecem iguais às do mês anterior, totalizando R$" + despesaMesAtual +
                        " É recomendável analisar os detalhes de suas receitas para manter uma gestão financeira equilibrada.";
            }
        }

        return conselho;
    }


    @Override
    public String gerarConselhoMetaDespesa(LocalDate dataInicio, LocalDate dataFinal) {
        String valorMetaStr = conselhosRepository.metaDefinidaDespesa();
        valorMetaStr = valorMetaStr.replaceAll("\\.", "").replace(",", ".");
        BigDecimal valorMeta = new BigDecimal(valorMetaStr);

        BigDecimal valorDespesaAtual = BigDecimal.valueOf(despesaRepository.totalDespesaPorPeriodo(dataInicio, dataFinal));
        String conselho = null;
        BigDecimal percentual;
        if (valorMeta.compareTo(valorDespesaAtual) > 0) {
            percentual = valorDespesaAtual.divide(valorMeta, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal diferenca = valorMeta.subtract(valorDespesaAtual);
            conselho = "Você gastou " + percentual.setScale(2, RoundingMode.HALF_UP) + "% da sua meta de despesa, representa um gasto de R$" + diferenca.setScale(2, RoundingMode.HALF_UP);
        } else {
            percentual = valorDespesaAtual.divide(valorMeta, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            BigDecimal diferenca = valorDespesaAtual.subtract(valorMeta);
            conselho = "Seu gasto ultrapassou a meta de despesa em " + percentual.setScale(2, RoundingMode.HALF_UP) + "%. Isso equivale a um excesso de gastos de R$" + diferenca.setScale(2, RoundingMode.HALF_UP) + ".";
        }
        return conselho;
    }

    @Override
    public String gerarConselhoMetaReceita(LocalDate dataInicio, LocalDate dataFinal) {
        String valorMetaStr = conselhosRepository.metaDefinidaReceita();
        valorMetaStr = valorMetaStr.replaceAll("\\.", "").replace(",", ".");
        BigDecimal valorMeta = new BigDecimal(valorMetaStr);

        BigDecimal valorReceitaRetornado = BigDecimal.valueOf(receitaRepository.totalReceitaPorPeriodo(dataInicio, dataFinal));

        String conselho = null;
        BigDecimal percentual;
        if (valorReceitaRetornado != null) {
            if (valorReceitaRetornado.compareTo(valorMeta) < 0) {
                percentual = valorReceitaRetornado.divide(valorMeta, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                BigDecimal diferenca = valorMeta.subtract(valorReceitaRetornado);
                conselho = "Você atingiu " + percentual.setScale(2, RoundingMode.HALF_UP) + "% da sua meta de receita, representando um " +
                        "total de R$" + valorReceitaRetornado.setScale(2, RoundingMode.HALF_UP) + ". Faltam R$" + diferenca.setScale(2, RoundingMode.HALF_UP) + " para alcançar sua meta.";
            } else {
                percentual = valorReceitaRetornado.divide(valorMeta, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                BigDecimal diferenca = valorReceitaRetornado.subtract(valorMeta);
                conselho = "Parabéns! Você alcançou " + percentual.setScale(2, RoundingMode.HALF_UP) + "% da sua meta de receita. Você " +
                        "excedeu sua meta em R$" + diferenca.setScale(2, RoundingMode.HALF_UP) + ".";
            }
        } else {
            conselho = "Não houve um valor de receita registrado em meta para ser atingindo.";
        }

        return conselho;
    }

    @Override
    public String despesaAReduzir(LocalDate dataInicio, LocalDate dataFinal) {
        Map<String, BigDecimal> despesasPorCategoria = new HashMap<>();

        List<Object[]> resultados = despesaRepository.encontrarDespesasPorCategoria(dataInicio, dataFinal);

        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            BigDecimal totalDespesa = (BigDecimal) resultado[1];
            despesasPorCategoria.put(categoria, totalDespesa);
        }

        LinkedHashMap<String, BigDecimal> despesasOrdenadas = despesasPorCategoria.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        StringBuilder conselhoBuilder = new StringBuilder("Suas maiores despesas são: ");
        int count = 0;
        for (Map.Entry<String, BigDecimal> entry : despesasOrdenadas.entrySet()) {
            if (count < 3) {
                conselhoBuilder.append(entry.getKey()).append(": R$").append(entry.getValue());
                if (count < 2) {
                    conselhoBuilder.append(", ");
                }
                count++;
            } else {
                break;
            }
        }
        conselhoBuilder.append(". Recomenda-se estudar maneiras de reduzir para alcançar um saldo positivo maior.");

        return conselhoBuilder.toString();
    }

    @Override
    public String receitasAAumentar(LocalDate dataInicio, LocalDate dataFinal) {
        Map<String, BigDecimal> receitasPorCategoria = new HashMap<>();

        List<Object[]> resultados = receitaRepository.encontrarReceitasPorCategoria(dataInicio, dataFinal);

        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            BigDecimal totalReceita = (BigDecimal) resultado[1];
            receitasPorCategoria.put(categoria, totalReceita);
        }

        LinkedHashMap<String, BigDecimal> receitasOrdenadas = receitasPorCategoria.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        StringBuilder conselhoBuilder = new StringBuilder("Suas maiores receitas são: ");
        int count = 0;
        for (Map.Entry<String, BigDecimal> entry : receitasOrdenadas.entrySet()) {
            if (count < 3) {
                conselhoBuilder.append(entry.getKey()).append(": R$").append(entry.getValue());
                if (count < 2) {
                    conselhoBuilder.append(", ");
                }
                count++;
            } else {
                break;
            }
        }
        conselhoBuilder.append(". Recomenda-se estudar maneiras de aumentar para obter um patrimônio maior.");

        return conselhoBuilder.toString();
    }

}