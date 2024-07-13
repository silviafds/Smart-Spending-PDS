package com.smartSpd.smartSpding.Core.Strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConselhosStrategyFactory {

    private final ConselhosStrategy hospitalConselhosStrategy;
    private final ConselhosStrategy restauranteConselhosStrategy;
    private final ConselhosStrategy supermercadoConselhosStrategy;

    public ConselhosStrategyFactory(
            @Qualifier("hospitalConselhosStrategy") ConselhosStrategy hospitalConselhosStrategy,
            @Qualifier("restauranteConselhosStrategy") ConselhosStrategy restauranteConselhosStrategy,
            @Qualifier("supermercadoConselhosStrategy") ConselhosStrategy supermercadoConselhosStrategy) {
        this.hospitalConselhosStrategy = hospitalConselhosStrategy;
        this.restauranteConselhosStrategy = restauranteConselhosStrategy;
        this.supermercadoConselhosStrategy = supermercadoConselhosStrategy;
    }

    public ConselhosStrategy getStrategy(String tipoConselho) {
        switch (tipoConselho.toUpperCase()) {
            case "HOSPITAL":
                return hospitalConselhosStrategy;
            case "RESTAURANTE":
                return restauranteConselhosStrategy;
            case "SUPERMERCADO":
                return supermercadoConselhosStrategy;
            default:
                throw new IllegalArgumentException("Tipo de conselho desconhecido: " + tipoConselho);
        }
    }
}
