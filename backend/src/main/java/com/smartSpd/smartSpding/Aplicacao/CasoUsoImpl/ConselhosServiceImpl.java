package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ConselhosService;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ConselhosRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ConselhosServiceImpl implements ConselhosService {

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ConselhosRepository conselhosRepository;

    public ConselhosServiceImpl(ConselhosRepository conselhosRepository) {
        this.conselhosRepository = conselhosRepository;
    }

    @Override
    public Boolean salvarConselhos(ConselhosDTO data) {
        try {
            if (data.identificador() == 1) {
                conselhosRepository.salvarConselhos(data.identificador(), data.statusDespesa(),
                        data.metaDespesa(), data.statusReceita(), data.metaReceita(), data.tempoConselho());
            }
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar dados de conselhos. ", e);
            return false;
        }
    }
}
