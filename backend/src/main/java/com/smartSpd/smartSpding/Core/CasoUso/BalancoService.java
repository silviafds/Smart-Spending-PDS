package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.Balanco;
import org.springframework.stereotype.Service;

@Service
public interface BalancoService {

    void salvarBalanco(Balanco balanco);
}
