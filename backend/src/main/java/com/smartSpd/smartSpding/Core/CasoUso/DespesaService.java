package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DespesaService {

    void cadastrarDespesa(DespesaDTO data) throws Excecoes;

    void editarDespesa(DespesaDTO data) throws Exception;

    void deletarDespesa(Long id);

    List<CategoriaDespesa> buscarTodasCategoriasDespesa();

    List<Despesa> buscarTodasAsDespesas();

    List<Despesa> buscarDespesasPorId(Integer id);
    List<?> buscarTodosTitulosContabeisDespesa(Integer id);
}