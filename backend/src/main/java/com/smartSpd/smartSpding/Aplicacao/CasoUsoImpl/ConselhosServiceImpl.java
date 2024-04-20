package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ConselhosRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.Balanco.DESPESA;

@Component
public class ConselhosServiceImpl implements ConselhosService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ConselhosRepository conselhosRepository;

    public ConselhosServiceImpl(ConselhosRepository conselhosRepository) {
        this.conselhosRepository = conselhosRepository;
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
        String excesso = avisarSobreExcessoDespesa(balancoRapidoDTO);
        conselhos.add(excesso);
        return conselhos;
    }

    @Override
    public String avisarSobreExcessoDespesa(BalancoRapidoDTO dado) {
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
            }
        }

        return conselho;
    }

}
