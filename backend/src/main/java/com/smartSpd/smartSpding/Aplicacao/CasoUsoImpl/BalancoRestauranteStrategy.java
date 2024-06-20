package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalancoRestauranteStrategy implements BalancoStrategy {
    @Override
    public List<BalancoDespesaReceita> obterDadosBalanco(BalancoRapidoDTO balancoRapidoDTO) {
        System.out.println("estrategia de restaurante");
        return null;
    }
}
