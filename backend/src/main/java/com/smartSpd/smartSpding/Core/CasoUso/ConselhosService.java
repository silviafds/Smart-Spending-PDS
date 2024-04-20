package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.ConselhosDTO;
import com.smartSpd.smartSpding.Core.Dominio.Conselhos;

import java.util.List;

public interface ConselhosService {

    Boolean salvarConselhos(ConselhosDTO data);

    List<Conselhos> buscarConfiguracaoConselhos();

    List<String> conselhosPorBalanco(BalancoRapidoDTO balancoRapidoDTO);

    String avisarSobreExcessoDespesa(BalancoRapidoDTO balancoRapidoDTO);
}