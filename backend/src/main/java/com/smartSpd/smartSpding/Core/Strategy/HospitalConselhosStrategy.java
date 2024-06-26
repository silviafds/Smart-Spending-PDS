package com.smartSpd.smartSpding.Core.Strategy;

import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import org.springframework.stereotype.Component;

@Component
    public class HospitalConselhosStrategy implements ConselhosStrategy {

        @Override
        public ConselhosDTO gerarConselho(ConselhosDTO data) {

            int metaDespesa = Integer.parseInt(data.metaDespesa());
            int metaReceita = Integer.parseInt(data.metaReceita());

            int precoMaquinarioNovo = 15000;
            if(metaDespesa + precoMaquinarioNovo < metaReceita){
                metaDespesa = metaDespesa + precoMaquinarioNovo;
            }
            String updatedMetaDespesa = String.format("%d", metaDespesa);
            return new ConselhosDTO(data.identificador(), data.statusDespesa(), updatedMetaDespesa, data.statusReceita(), data.metaReceita(), data.tempoConselho());
        }
    }

