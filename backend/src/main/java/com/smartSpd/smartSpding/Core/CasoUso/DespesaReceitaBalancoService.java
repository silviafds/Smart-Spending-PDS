package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaReceitaBalancoService {

    List<BalancoDespesaReceita> buscarDadosReceitaDespesa(BalancoDTO balancoRapidoDTO);

    void salvarDadosBalanco(BalancoDTO balancoDTO);

}
