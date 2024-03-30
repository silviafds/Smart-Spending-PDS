package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDespesaReceitaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaReceitaBalancoService {

    List<BalancoDespesaReceita> buscarDadosReceitaDespesa(BalancoRapidoDespesaReceitaDTO balancoRapidoDespesaReceitaDTO);
}
