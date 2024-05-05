package com.smartSpd.smartSpding.Infraestructure.Repositorio;

import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa(c.id, c.nome) FROM categoria_despesa c")
    List<CategoriaDespesa> buscarTodasAsCategoriaDespesa();

    @Query("SELECT new com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa(c.id, c.nome) FROM categoria_despesa c where c.id = :id")
    List<CategoriaDespesa> buscarCategoriaPorId(Long id);

    @Modifying
    @Query("UPDATE categoria_despesa cd SET cd.nome = :nome WHERE cd.id = :idCategoria")
    int editarCategoriaDespesa(
            @Param("idCategoria") Long idCategoria,
            @Param("nome") String nome
    );

}
