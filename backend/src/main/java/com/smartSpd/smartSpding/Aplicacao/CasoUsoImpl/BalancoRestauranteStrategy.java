package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BalancoRestauranteStrategy implements BalancoStrategy {
    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    public BalancoRestauranteStrategy(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    public List<?> gerenciadorTipoBalanco(BalancoRapidoDTO balancoRapidoDTO) {
        if(balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.TREINAMENTO_FUNCIONARIOS.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.DECORACAO_AMBIENTE.getTiposBalanco())) {
            return gerarBalancoDespesa(balancoRapidoDTO);
        }

        return gerarBalancoInvestimento(balancoRapidoDTO);
    }
    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        List<BalancoDespesaReceita> balancos = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);

        for (BalancoDespesaReceita balanco : balancos) {
            if (balanco.getValor() != null) {
                BigDecimal valor = BigDecimal.valueOf(balanco.getValor());
                BigDecimal imposto = valor.multiply(BigDecimal.valueOf(0.025)) // cálculo de 2,5%
                        .setScale(2, RoundingMode.HALF_UP);
                balanco.setImposto(imposto);
                balanco.setLucro(BigDecimal.valueOf(0.0));
            } else {
                balanco.setImposto(BigDecimal.ZERO);
            }
        }

        return balancos;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO) {
        List<BalancoDespesaReceita> balancos = despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);

        for (BalancoDespesaReceita balanco : balancos) {
            if (balanco.getValor() != null) {
                BigDecimal valor = BigDecimal.valueOf(balanco.getValor());
                BigDecimal lucro = valor.multiply(BigDecimal.valueOf(0.05)) // cálculo de 5%
                        .setScale(2, RoundingMode.HALF_UP);
                balanco.setLucro(lucro);
                balanco.setImposto(BigDecimal.valueOf(0.0));
            } else {
                balanco.setImposto(BigDecimal.ZERO);
            }
        }

        return balancos;
    }
}
