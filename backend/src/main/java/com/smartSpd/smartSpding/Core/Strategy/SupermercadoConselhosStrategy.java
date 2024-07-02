package com.smartSpd.smartSpding.Core.Strategy;

import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("supermercadoConselhosStrategy")
public class SupermercadoConselhosStrategy implements ConselhosStrategy {
    @Override
    public ConselhosDTO gerarConselho(ConselhosDTO data) {
        int metaDespesa = Integer.parseInt(data.metaDespesa());
        int alimentosPerdidos = 3000;

        metaDespesa += alimentosPerdidos;
        String updatedMetaDespesa = String.format("%d", metaDespesa);
        return new ConselhosDTO(data.identificador(), data.statusDespesa(), updatedMetaDespesa, data.statusReceita(), data.metaReceita(), data.tempoConselho(), data.tipoConselho());
    }
}

