package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaException;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaInvalidaException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaDespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaReceitaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class GerenciadorCategoria {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final CategoriaReceitaRepository categoriaReceitaRepository;

    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public GerenciadorCategoria(CategoriaReceitaRepository categoriaReceitaRepository,
                                CategoriaDespesaRepository categoriaDespesaRepository) {
        this.categoriaReceitaRepository = categoriaReceitaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    public boolean verificaCamposObrigatorios(CategoriaDTO catagoria) {

        if(catagoria.getNomeCategoria().isEmpty() || catagoria.getTipoCategoria().isEmpty() ||
           catagoria.getNomeCategoria() == null || catagoria.getTipoCategoria() == null) {
            return false;
        }
        return true;
    }

    public CategoriaReceita mapeiaDTOparaCategoriaReceita(CategoriaDTO categoria) {
        CategoriaReceita categoriaReceita = new CategoriaReceita();
        categoriaReceita.setNome(categoria.getNomeCategoria());
        return categoriaReceita;
    }

    public CategoriaDespesa mapeiaDTOparaCategoriaDespesa(CategoriaDTO categoria) {
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setNome(categoria.getNomeCategoria());
        return categoriaDespesa;
    }

    public void validarEntrada(Long data) {
        if (data == null) {
            log.severe("Tentativa de editar categoria sem ID.");
            throw new NullPointerException("ID da categoria ao editar não pode ser nulo.");
        }
    }

    public String toUpperCase(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return palavra;
        }
        return palavra.toUpperCase();
    }




    public boolean verificaExistenciaCategoriaReceita(String categoria) throws CategoriaInvalidaException {
        List<Object[]> verificador = categoriaReceitaRepository.verificaExistenciaCategoria(categoria);
        for (Object[] array : verificador) {
            for (Object obj : array) {
                if (obj instanceof Boolean && (Boolean) obj) {
                    throw new CategoriaInvalidaException("Esta categoria já esta cadastrada no sistema.");
                }
            }
        }
        return true;
    }

    public boolean verificaExistenciaCategoriaDespesa(String categoria) throws CategoriaInvalidaException {
        List<Object[]> verificador = categoriaDespesaRepository.verificaExistenciaCategoria(categoria);
        for (Object[] array : verificador) {
            for (Object obj : array) {
                if (obj instanceof Boolean && (Boolean) obj) {
                    throw new CategoriaInvalidaException("Esta categoria já esta cadastrada no sistema.");
                }
            }
        }
        return true;
    }

}