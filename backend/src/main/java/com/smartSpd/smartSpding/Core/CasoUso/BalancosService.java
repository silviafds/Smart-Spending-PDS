package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import org.springframework.stereotype.Service;

@Service
public interface BalancosService {

    void registrarBalanco(Balancos balancos);

}
