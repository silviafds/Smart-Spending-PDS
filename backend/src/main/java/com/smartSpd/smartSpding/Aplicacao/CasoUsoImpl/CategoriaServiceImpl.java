package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorCategoria;
import com.smartSpd.smartSpding.Core.CasoUso.CategoriaService;
import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaException;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaInvalidaException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaDespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CategoriaServiceImpl implements CategoriaService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final CategoriaReceitaRepository categoriaReceitaRepository;
    private final CategoriaDespesaRepository categoriaDespesaRepository;
    private final GerenciadorCategoria gerenciadorCategoria;

    public CategoriaServiceImpl(CategoriaReceitaRepository categoriaReceitaRepository,
                                GerenciadorCategoria gerenciadorCategoria,
                                CategoriaDespesaRepository categoriaDespesaRepository) {
        this.categoriaReceitaRepository = categoriaReceitaRepository;
        this.gerenciadorCategoria = gerenciadorCategoria;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }

    @Override
    public void salvarCategoria(CategoriaDTO data) {
        try {
            boolean validaCategoria = gerenciadorCategoria.verificaCamposObrigatorios(data);

            if(validaCategoria) {
                switch(data.getTipoCategoria()) {
                    case "Receita":
                        data.setNomeCategoria(gerenciadorCategoria.toUpperCase(data.getNomeCategoria()));
                        try {
                            boolean verificador = gerenciadorCategoria.verificaExistenciaCategoriaReceita(data.getNomeCategoria());

                            if(verificador) {
                                CategoriaReceita categoriaReceita = gerenciadorCategoria.mapeiaDTOparaCategoriaReceita(data);
                                categoriaReceitaRepository.save(categoriaReceita);
                            }

                        } catch (CategoriaInvalidaException e) {
                            log.log(Level.SEVERE, "Erro ao cadastrar categoria já existente. ", e);
                            throw e;
                        }
                        break;
                    case "Despesa":
                        CategoriaDespesa categoriaDespesa = gerenciadorCategoria.mapeiaDTOparaCategoriaDespesa(data);
                        categoriaDespesaRepository.save(categoriaDespesa);
                        break;
                    default:
                        log.log(Level.WARNING, "Valor informado é inválido. ", true);
                        break;
                }
            }

        } catch (CategoriaException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova categoria no service. ", e);
            throw e;
        }
    }


    /*@Override
    public void salvarCategoria(CategoriaDTO data) {
        try {
            boolean validaCategoria = gerenciadorCategoria.verificaCamposObrigatorios(data);

            if(validaCategoria) {
                switch(data.getTipoCategoria()) {
                    case "Receita":
                        data.setNomeCategoria(gerenciadorCategoria.toUpperCase(data.getNomeCategoria()));
                        boolean verificador = gerenciadorCategoria.verificaExistenciaCategoriaReceita(data.getNomeCategoria());

                        if(verificador) {
                            CategoriaReceita categoriaReceita = gerenciadorCategoria.mapeiaDTOparaCategoriaReceita(data);
                            categoriaReceitaRepository.save(categoriaReceita);
                        }

                        break;
                    case "Despesa":
                        CategoriaDespesa categoriaDespesa = gerenciadorCategoria.mapeiaDTOparaCategoriaDespesa(data);
                        categoriaDespesaRepository.save(categoriaDespesa);
                        break;
                    default:
                        log.log(Level.WARNING, "Valor informado é inválido. ", true);
                        break;
                }
            }

        } catch (CategoriaException e) {
            throw e;
        } catch (CategoriaInvalidaException e) {
            log.log(Level.SEVERE, "Erro ao cadastrar categoria ja existente. ", e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova categoria no service. ", e);
            throw e;
        }

    }*/

    @Override
    public void editarCategoria(CategoriaDTO data) {
        try {
            gerenciadorCategoria.validarEntrada(data.getId());

            boolean validaCategoria = gerenciadorCategoria.verificaCamposObrigatorios(data);

            if(validaCategoria) {
                switch(data.getTipoCategoria()) {
                    case "Receita":
                        int receita = categoriaReceitaRepository.editarCategoriaReceita(data.getId(), data.getNomeCategoria());
                        break;
                    case "Despesa":
                        int despesa = categoriaDespesaRepository.editarCategoriaDespesa(data.getId(), data.getNomeCategoria());
                        break;
                    default:
                        log.log(Level.WARNING, "Valor informado é inválido. ", true);
                        break;
                }
            }
        } catch (CategoriaException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar categoria no service. ", e);
            throw e;
        }
    }

    @Override
    public void deletarCategoria(Long id, String tipoCategoria) {
        gerenciadorCategoria.validarEntrada(id);

        switch(tipoCategoria) {
            case "Receita":
                Optional<CategoriaReceita> categoriaReceita = categoriaReceitaRepository.findById(id);
                if (!categoriaReceita.isPresent()) {
                    throw new CategoriaException("Categoria com ID " + id + " não encontrada.");
                }
                categoriaReceitaRepository.delete(categoriaReceita.get());
                break;
            case "Despesa":
                Optional<CategoriaDespesa> categoriaDespesa = categoriaDespesaRepository.findById(id);
                if (!categoriaDespesa.isPresent()) {
                    throw new CategoriaException("Categoria com ID " + id + " não encontrada.");
                }
                categoriaDespesaRepository.delete(categoriaDespesa.get());
                break;
            default:
                log.log(Level.WARNING, "Valor informado é inválido. ", true);
                break;
        }
    }

    @Override
    public List<CategoriaReceita> buscarTodasCategoriasReceitas() {
        return categoriaReceitaRepository.buscarTodasAsCategoriaReceita();
    }

    @Override
    public List<CategoriaDespesa> buscarTodasCategoriasDespesa() {
        return categoriaDespesaRepository.buscarTodasAsCategoriaDespesa();
    }

    @Override
    public List<CategoriaReceita> buscarCategoriaReceitaPorID(Long id) {
        return categoriaReceitaRepository.buscarCategoriaPorId(id);
    }

    @Override
    public List<CategoriaDespesa> buscarCategoriaDespesaPorID(Long id) {
        return categoriaDespesaRepository.buscarCategoriaPorId(id);
    }

}
