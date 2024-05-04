package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Dominio.Projetos;

import java.util.List;

public interface ProjetosService {

    void salvarProjetos(Projetos data);

    List<Projetos> buscarTodosProjetos();

    void editarProjeto(Projetos data);

    void deletarProjeto(Long id);

    List<Projetos> buscarProjetoPorId(Long id);

}