package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestauranteBalancosStrategyImpl implements BalancoStrategy {

    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    public RestauranteBalancosStrategyImpl(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.TREINAMENTO_FUNCIONARIOS.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.MARKETING_PROPAGANDA.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.DECORACAO_AMBIENTE.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }
        return null;    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.TREINAMENTO_FUNCIONARIOS.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.MARKETING_PROPAGANDA.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.DECORACAO_AMBIENTE.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }
        return null;
    }
}
