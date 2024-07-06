package com.smartSpd.smartSpding.Core.Strategy;

import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("restauranteConselhosStrategy")
public class RestauranteConselhosStrategy implements ConselhosStrategy{
    @Override
    public ConselhosDTO gerarConselho(ConselhosDTO data) {
        int metaDespesa = Integer.parseInt(data.metaDespesa());
        int metaReceita = Integer.parseInt(data.metaReceita());

        if((double) metaDespesa /metaReceita <= 0.3){
            metaDespesa += (int) (metaReceita*0.3);
        }
        String updatedMetaDespesa = String.format("%d", metaDespesa);
        return new ConselhosDTO(data.identificador(), data.statusDespesa(), updatedMetaDespesa, data.statusReceita(), data.metaReceita(), data.tempoConselho(), data.tipoConselho());
    }
}
