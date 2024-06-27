package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoStrategy;
import com.smartSpd.smartSpding.Core.CasoUso.BalancosService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.ORIGENS_MAIS_RENTAVEIS;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.PAGAMENTOS_MAIS_UTILIZADOS;

@Component
public class BalancosStrategyImpl implements BalancoStrategy {

    private final DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService;

    @Autowired
    public BalancosStrategyImpl(DespesaReceitaBalancoServiceImpl despesaReceitaBalancoService) {
        this.despesaReceitaBalancoService = despesaReceitaBalancoService;
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO) {
        return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
    }

    @Override
    public List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO) {
        return despesaReceitaBalancoService.buscarDadosReceitaDespesa(balancoRapidoDTO);
    }
}