package com.smartSpd.smartSpding.Aplicacao.casoDeUso;

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

    Boolean deletarReceita(Long id);

    List<CategoriaReceita> buscarTodasCategoriasReceitas();

    List<Receita> buscarTodasAsReceitas();

    List<Receita> buscarReceitasPorId(Integer id);

    List<TituloContabilReceita> buscarTodosTitulosContabeisReceitas(Integer id);

}
