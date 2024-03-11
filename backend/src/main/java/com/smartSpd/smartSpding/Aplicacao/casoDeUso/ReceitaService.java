package com.smartSpd.smartSpding.Aplicacao.casoDeUso;

import com.smartSpd.smartSpding.Core.DTO.EditarReceitaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReceitaService {

    Boolean cadastrarReceita(ReceitaDTO data);

    Boolean editarReceita(ReceitaDTO data);

    Boolean deletarReceita(EditarReceitaDTO data);

    List<CategoriaReceita> buscarTodasCategoriasReceitas();

    List<Receita> buscarTodasAsReceitas();

    List<Receita> buscarReceitasPorId(int id);

    List<TituloContabilReceita> buscarTodosTitulosContabeisReceitas(int id);

}
