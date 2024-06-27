package com.smartSpd.smartSpding.Infraestructure.Strategy;

import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;

import java.util.List;

public class BalancoGastosStrategy implements BalancoStrategy {
    private final BalancosRepository balancosRepository;

    public BalancoGastosStrategy(BalancosRepository balancosRepository) {
        this.balancosRepository = balancosRepository;
    }

    @Override
    public List<Object[]> calcularBalanco() {
        return balancosRepository.somarGastosPorCategoria();
    }
}
