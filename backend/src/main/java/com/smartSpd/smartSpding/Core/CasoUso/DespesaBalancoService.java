package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaBalancoService {

    List<BalancoDespesa> listagemMeiosPagamento();

    List<BalancoDespesa> balancoMeiosPagamento(BalancoDTO balancoRapidoDTO);
}