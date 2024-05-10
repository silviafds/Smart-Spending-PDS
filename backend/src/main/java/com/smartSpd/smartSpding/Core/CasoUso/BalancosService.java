package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BalancosService {

    void registrarBalanco(Balancos balancos);

    void editarBalanco(Balancos balancos);

    List<Balancos> buscarTodosBalancos();

    Balancos buscarBalancoPorId(int id);

    void deletarBalanco(int id);
}
