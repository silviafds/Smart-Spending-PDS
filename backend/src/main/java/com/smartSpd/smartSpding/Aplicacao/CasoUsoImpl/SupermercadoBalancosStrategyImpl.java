package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SupermercadoBalancosStrategyImpl implements BalancoStrategy {

    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    public SupermercadoBalancosStrategyImpl(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.ENTREGA.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.RELACIONAMENTO_CLIENTES.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.SERVICOS_TERCEIRIZADOS.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }
        return null;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO) {
        if (balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.ENTREGA.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.RELACIONAMENTO_CLIENTES.getTiposBalanco()) ||
                balancoRapidoDTO.getAnaliseBalanco().equals(TiposBalanco.SERVICOS_TERCEIRIZADOS.getTiposBalanco())) {
            return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
        }
        return null;
    }
}
