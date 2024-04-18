package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.ContaInternaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContaInternaService {

    Boolean cadastrarContaInterna(ContaInternaDTO data);

    Boolean editarContaInterna(ContaInternaDTO data);

    Boolean editarStatusContaInterna(ContaInternaDTO data);

    Boolean deletarContaInterna(int data);

    List<ContaInterna> buscarTodasContasInterna();

    List<ContaInterna> buscarTodasContasInternaHabilitadas();

    ContaInterna buscarContaInternaPeloId(int data);

}
