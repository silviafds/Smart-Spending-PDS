package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancosService;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalancosServiceImpl implements BalancosService {

    private final BalancosRepository balancosRepository;

    @Autowired
    public BalancosServiceImpl(BalancosRepository balancosRepository) {
        this.balancosRepository = balancosRepository;
    }

    @Override
    public void registrarBalanco(Balancos balancos) {

    }
}
