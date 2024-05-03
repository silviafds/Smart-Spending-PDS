package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReceitaBalancoService {

    /*List<BalancoDespesa> listagemMeiosPagamento();*/

    List<BalancoReceita> balancoMeiosPagamento(BalancoDTO balancoRapidoDTO);
}