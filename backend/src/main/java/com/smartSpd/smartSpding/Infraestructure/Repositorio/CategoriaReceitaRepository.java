package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaReceitaRepository extends JpaRepository<CategoriaReceita, Long> {

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita(c.id, c.nome) FROM categoria_receita c")
    List<CategoriaReceita> buscarTodasAsCategoriaReceita();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita(c.id, c.nome) FROM categoria_receita c where c.id = :id")
    List<CategoriaReceita> buscarCategoriaPorId(Long id);

    @Modifying
    @Query("UPDATE categoria_receita cr SET cr.nome = :nome WHERE cr.id = :idCategoria")
    int editarCategoriaReceita(Long idCategoria, @Param("nome") String nome);

    @Query("SELECT CASE WHEN cr.nome = :nome THEN true ELSE false END FROM categoria_receita cr")
    List<Object[]> verificaExistenciaCategoria(@Param("nome") String categoria);
}