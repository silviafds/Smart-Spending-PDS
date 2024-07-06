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
public class BalancoSupermercadoStrategy implements BalancoStrategy {
    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    public BalancoSupermercadoStrategy(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    public List<?> gerenciadorTipoBalanco(BalancoRapidoDTO balancoRapidoDTO) {
        if(balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.ENTREGA.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.SERVICOS_TERCEIRIZADOS.getTiposBalanco())) {
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
                BigDecimal imposto = valor.multiply(BigDecimal.valueOf(0.03)) // cálculo de 3%
                        .setScale(2, RoundingMode.HALF_UP);
                balanco.setImpostoTotal(imposto);
            } else {
                balanco.setImpostoTotal(BigDecimal.ZERO);
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
                BigDecimal imposto = valor.multiply(BigDecimal.valueOf(0.06)) // cálculo de 6%
                        .setScale(2, RoundingMode.HALF_UP);
                balanco.setImpostoTotal(imposto);
            } else {
                balanco.setImpostoTotal(BigDecimal.ZERO);
            }
        }

        return balancos;
    }
}
