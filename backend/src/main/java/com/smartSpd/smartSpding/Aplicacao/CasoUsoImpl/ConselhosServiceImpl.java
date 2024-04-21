package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorConselhos;
import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ConselhosRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.smartSpd.smartSpding.Core.Enum.Balanco.DESPESA;

@Component
public class ConselhosServiceImpl implements ConselhosService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ConselhosRepository conselhosRepository;

    private final GerenciadorConselhos gerenciadorConselhos;

    private final DespesaRepository despesaRepository;

    private final ReceitaRepository receitaRepository;

    public ConselhosServiceImpl(ConselhosRepository conselhosRepository, GerenciadorConselhos gerenciadorConselhos,
                                DespesaRepository despesaRepository, ReceitaRepository receitaRepository) {
        this.conselhosRepository = conselhosRepository;
        this.gerenciadorConselhos = gerenciadorConselhos;
        this.despesaRepository = despesaRepository;
        this.receitaRepository = receitaRepository;
    }

    @Override
    public Boolean salvarConselhos(ConselhosDTO data) {
        try {
            if (data.identificador() == 1) {
                conselhosRepository.salvarConselhos(data.identificador(), data.statusDespesa(),
                        data.metaDespesa(), data.statusReceita(), data.metaReceita(), data.tempoConselho());
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
        String excesso = avisarSobreExcessoDespesaEReceita(balancoRapidoDTO);
        String comparaDespesaAtualEAnterior = compararDespesaMesAtualEAnterior(balancoRapidoDTO);
        String comparaReceitaAtualEAnterior = compararReceitaMesAtualEAnterior(balancoRapidoDTO);

        conselhos.add(excesso);
        conselhos.add(comparaDespesaAtualEAnterior);
        conselhos.add(comparaReceitaAtualEAnterior);

        boolean verificaMetaDespesa = gerenciadorConselhos.verificacaoMetaDespesa();
        if(verificaMetaDespesa) {
            String conselhoMeta = gerarConselhoMetaDespesa(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
            conselhos.add(conselhoMeta);
        }

        boolean verificaMetaReceita = gerenciadorConselhos.verificacaoMetaReceita();
        if(verificaMetaReceita) {
            String conselhoMeta = gerarConselhoMetaReceita(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
            conselhos.add(conselhoMeta);
        }
        String despesasPorCategoria = despesaAReduzir(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
        conselhos.add(despesasPorCategoria);

        String receitasPorCategoria = receitasAAumentar(balancoRapidoDTO.getDataInicio(), balancoRapidoDTO.getDataTermino());
        conselhos.add(receitasPorCategoria);


        return conselhos;
    }

    @Override
    public String avisarSobreExcessoDespesaEReceita(BalancoRapidoDTO dado) {
        List<Object[]> valoresBalanco = conselhosRepository.calcularTotalPorPeriodo(dado.getDataInicio(), dado.getDataTermino());

        Object[] balanco = valoresBalanco.get(0);

        Double totalDespesa = (Double) balanco[0];
        Double totalReceita = (Double) balanco[1];
        String conselho = null;

        if (dado.getTipoBalanco().equals(DESPESA.getBalanco())) {
            if (totalDespesa > totalReceita) {
                double diferenca = totalDespesa - totalReceita;
                conselho = "O montante de despesa superou o de receita em R$" + diferenca;
            } else if (totalReceita > totalDespesa) {
                double diferenca = totalReceita - totalDespesa;
                conselho = "O montante de receita superou o de despesa em R$" + diferenca;
            } else {
                conselho = "Atenção, sua despesa e receita atigiram mesmo valor.";
            }
        }



        return conselho;
    }

    @Override
    public String compararDespesaMesAtualEAnterior(BalancoRapidoDTO dado) {
        String quantidadeDeMesAnterior = gerenciadorConselhos.buscarData(dado.getDataInicio());

        List<LocalDate> listaDataMesAnterior = gerenciadorConselhos.montarDataQuery(dado.getDataInicio(), quantidadeDeMesAnterior);

        List<Object[]> teste = conselhosRepository.avisoDespesaSuperiorMesAnteriorCategoriaMaiorGasto(dado.getDataInicio(),
                dado.getDataTermino(), listaDataMesAnterior.get(0), listaDataMesAnterior.get(1));

        Object[] balanco = teste.get(0);

        Double despesaMesAtual = (Double) balanco[0];
        Double despesaMesAnterior = (Double) balanco[1];

        String conselho = null;

        if (despesaMesAtual > despesaMesAnterior) {
            double diferenca = despesaMesAtual - despesaMesAnterior;
            conselho = "ATENÇÃO: Sua despesa atual ultrapassou a despesa dos mês anterior em R$" + diferenca;
        } else if (despesaMesAnterior > despesaMesAtual) {
            double diferenca = despesaMesAnterior - despesaMesAtual;
            conselho = "Parabéns! Sua despesa no período de " + gerenciadorConselhos.formataData(dado.getDataInicio())
                    + " a "+ gerenciadorConselhos.formataData(dado.getDataTermino()) +" permanece inferior à do mês anterior, com uma diferença de R$" + diferenca;
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

        Double despesaMesAtual = (Double) balanco[0];
        Double despesaMesAnterior = (Double) balanco[1];

        String conselho = null;

        if (despesaMesAtual > despesaMesAnterior) {
            double diferenca = despesaMesAtual - despesaMesAnterior;
            conselho = "Parabéns: Sua receita atual ultrapassou a receita dos mês anterior em R$" + diferenca;
        } else if (despesaMesAnterior > despesaMesAtual) {
            double diferenca = despesaMesAnterior - despesaMesAtual;
            conselho = "Atenção! Sua receita no período de " + gerenciadorConselhos.formataData(dado.getDataInicio())
                    + " a "+ gerenciadorConselhos.formataData(dado.getDataTermino()) +" permanece inferior à do mês anterior, com uma diferença de R$" + diferenca;
        }

        return conselho;
    }

    @Override
    public String gerarConselhoMetaDespesa(LocalDate dataInicio, LocalDate dataFinal) {
        String valorMetaStr = conselhosRepository.metaDefinidaDespesa();
        valorMetaStr = valorMetaStr.replaceAll("\\.", "");
        valorMetaStr = valorMetaStr.replace(",", ".");
        double valorMeta = Double.parseDouble(valorMetaStr);

        double valorDespesaAtual = despesaRepository.totalDespesaPorPeriodo(dataInicio, dataFinal);
        String conselho = null;
        double percentual;
        if (valorMeta > valorDespesaAtual) {
            percentual = ((valorDespesaAtual / valorMeta) * 100);
            double diferenca = valorMeta-valorDespesaAtual;
            conselho = "Você gastou " + String.format("%.2f", percentual) + "% da sua meta de despesa, representa um gasto de R$" + diferenca;
        } else {
            percentual = ((valorDespesaAtual / valorMeta) * 100);
            double diferenca = valorDespesaAtual - valorMeta;
            conselho = "Seu gasto ultrapassou a meta de despesa em " + String.format("%.2f", percentual) + "%. Isso equivale a um excesso de gastos de R$" + diferenca + ".";
        }
        return conselho;
    }

    @Override
    public String gerarConselhoMetaReceita(LocalDate dataInicio, LocalDate dataFinal) {
        String valorMetaStr = conselhosRepository.metaDefinidaReceita();
        valorMetaStr = valorMetaStr.replaceAll("\\.", "");
        valorMetaStr = valorMetaStr.replace(",", ".");
        double valorMeta = Double.parseDouble(valorMetaStr);

        double valorReceitaAtual = receitaRepository.totalReceitaPorPeriodo(dataInicio, dataFinal);
        String conselho = null;
        double percentual;
        if (valorReceitaAtual < valorMeta) {
            percentual = ((valorReceitaAtual / valorMeta) * 100);
            double diferenca = valorMeta - valorReceitaAtual;
            conselho = "Você atingiu " + String.format("%.2f", percentual) + "% da sua meta de receita, representando um " +
                    "total de R$" + valorReceitaAtual + ". Faltam R$" + diferenca + " para alcançar sua meta.";
        } else {
            percentual = ((valorReceitaAtual / valorMeta) * 100);
            double diferenca = valorReceitaAtual - valorMeta;
            conselho = "Parabéns! Você alcançou " + String.format("%.2f", percentual) + "% da sua meta de receita. Você " +
                    "excedeu sua meta em R$" + diferenca + ".";
        }
        return conselho;
    }

    @Override
    public String despesaAReduzir(LocalDate dataInicio, LocalDate dataFinal) {
        Map<String, Double> despesasPorCategoria = new HashMap<>();

        List<Object[]> resultados = despesaRepository.encontrarDespesasPorCategoria(dataInicio, dataFinal);

        // Preencher o mapa com os resultados
        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            Double totalDespesa = (Double) resultado[1];
            despesasPorCategoria.put(categoria, totalDespesa);
        }

        // Ordenar o mapa por valor (total de despesa) em ordem decrescente
        LinkedHashMap<String, Double> despesasOrdenadas = despesasPorCategoria.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Montar o conselho com as maiores despesas por categoria
        StringBuilder conselhoBuilder = new StringBuilder("Suas maiores despesas são: ");
        int count = 0;
        for (Map.Entry<String, Double> entry : despesasOrdenadas.entrySet()) {
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
        Map<String, Double> despesasPorCategoria = new HashMap<>();

        List<Object[]> resultados = receitaRepository.encontrarReceitasPorCategoria(dataInicio, dataFinal);

        // Preencher o mapa com os resultados
        for (Object[] resultado : resultados) {
            String categoria = (String) resultado[0];
            Double totalDespesa = (Double) resultado[1];
            despesasPorCategoria.put(categoria, totalDespesa);
        }

        // Ordenar o mapa por valor (total de despesa) em ordem decrescente
        LinkedHashMap<String, Double> despesasOrdenadas = despesasPorCategoria.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Montar o conselho com as maiores despesas por categoria
        StringBuilder conselhoBuilder = new StringBuilder("Suas maiores receitas são: ");
        int count = 0;
        for (Map.Entry<String, Double> entry : despesasOrdenadas.entrySet()) {
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
