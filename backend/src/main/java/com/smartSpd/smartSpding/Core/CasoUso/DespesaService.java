package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaService {

    Boolean cadastrarDespesa(DespesaDTO data) throws Excecoes;

    Boolean editarDespesa(DespesaDTO data);

    Boolean deletarDespesa(Long id);

    List<CategoriaDespesa> buscarTodasCategoriasDespesa();

    List<Despesa> buscarTodasAsDespesas();

   List<Despesa> buscarDespesasPorId(Integer id);
    List<TituloContabilDespesa> buscarTodosTitulosContabeisDespesa(Integer id);
}
