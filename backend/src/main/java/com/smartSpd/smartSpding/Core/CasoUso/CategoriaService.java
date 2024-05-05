package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;

import java.util.List;

public interface CategoriaService {

    void salvarCategoria(CategoriaDTO data);

    void editarCategoria(CategoriaDTO data);

    void deletarCategoria(Long id, String tipoCategoria);

    List<CategoriaReceita> buscarTodasCategoriasReceitas();

    List<CategoriaDespesa> buscarTodasCategoriasDespesa();

    List<CategoriaReceita> buscarCategoriaReceitaPorID(Long id);

    List<CategoriaDespesa> buscarCategoriaDespesaPorID(Long id);

}