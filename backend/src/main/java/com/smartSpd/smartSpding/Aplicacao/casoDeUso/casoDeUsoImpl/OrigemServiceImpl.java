package com.smartSpd.smartSpding.Aplicacao.casoDeUso.casoDeUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.casoDeUso.OrigemService;
import com.smartSpd.smartSpding.Core.Dominio.Origem;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.OrigemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrigemServiceImpl implements OrigemService {

    private final OrigemRepository origemRepository;

    public OrigemServiceImpl(OrigemRepository origemRepository) {
        this.origemRepository = origemRepository;
    }

    @Override
    public List<Origem> buscarTodasOrigens() {
        return origemRepository.findByAllOrigem();
    }
}
