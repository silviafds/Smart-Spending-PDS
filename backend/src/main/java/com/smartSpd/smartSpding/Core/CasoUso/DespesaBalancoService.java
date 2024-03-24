package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaBalancoService {

    List<BalancoDespesa> listagemMeiosPagamento();
}