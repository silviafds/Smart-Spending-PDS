package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.Balanco;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BalancoService {

    void salvarBalanco(Balanco balanco);

    Balanco buscarBalancoPorId(Long id);

    List<Balanco> buscarTodosBalancos();

}
