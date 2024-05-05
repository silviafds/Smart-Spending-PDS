package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class GerenciadorCategoria {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    public boolean verificaCamposObrigatorios(CategoriaDTO catagoria) {

        if(catagoria.nomeCategoria().isEmpty() || catagoria.tipoCategoria().isEmpty() ||
           catagoria.nomeCategoria() == null || catagoria.tipoCategoria() == null) {
            return false;
        }
        return true;
    }

    public CategoriaReceita mapeiaDTOparaCategoriaReceita(CategoriaDTO categoria) {
        CategoriaReceita categoriaReceita = new CategoriaReceita();
        categoriaReceita.setNome(categoria.nomeCategoria());
        return categoriaReceita;
    }

    public CategoriaDespesa mapeiaDTOparaCategoriaDespesa(CategoriaDTO categoria) {
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setNome(categoria.nomeCategoria());
        return categoriaDespesa;
    }

    public void validarEntrada(Long data) {
        if (data == null) {
            log.severe("Tentativa de editar categoria sem ID.");
            throw new NullPointerException("ID da categoria ao editar não pode ser nulo.");
        }
    }

}
