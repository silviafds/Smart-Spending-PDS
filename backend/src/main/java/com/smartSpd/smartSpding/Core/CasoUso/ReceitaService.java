package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReceitaService {

    Boolean cadastrarReceita(ReceitaDTO data) throws Excecoes;

    Boolean editarReceita(ReceitaDTO data);

    Boolean deletarReceita(Long id);

    List<CategoriaReceita> buscarTodasCategoriasReceitas();

    List<Receita> buscarTodasAsReceitas();

    List<Receita> buscarReceitasPorId(Integer id);

    List<TituloContabilReceita> buscarTodosTitulosContabeisReceitas(Integer id);

}
