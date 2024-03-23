package com.smartSpd.smartSpding.Aplicacao.casoDeUso;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DespesaService {

    Boolean cadastrarDespesa(DespesaDTO data);

    Boolean editarDespesa(DespesaDTO data);

    Boolean deletarDespesa(Long id);

    List<CategoriaDespesa> buscarTodasCategoriasDespesa();

    List<Despesa> buscarTodasAsDespesas();

   List<Despesa> buscarDespesasPorId(Integer id);
    List<TituloContabilDespesa> buscarTodosTitulosContabeisDespesa(Integer id);
}
