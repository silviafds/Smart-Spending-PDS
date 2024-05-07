package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorCategoria;
import com.smartSpd.smartSpding.Core.CasoUso.CategoriaService;
import com.smartSpd.smartSpding.Core.Classes.Categorias;
import com.smartSpd.smartSpding.Core.DTO.CategoriaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaDespesa;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaException;
import com.smartSpd.smartSpding.Core.Excecao.CategoriaInvalidaException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaDespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.CategoriaReceitaRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.*;
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
                        data.setNomeCategoria(gerenciadorCategoria.toUpperCase(data.getNomeCategoria()));

                        try {
                            boolean verificador = gerenciadorCategoria.verificaExistenciaCategoriaDespesa(data.getNomeCategoria());

                            if(verificador) {
                                CategoriaDespesa categoriaDespesa = gerenciadorCategoria.mapeiaDTOparaCategoriaDespesa(data);
                                categoriaDespesaRepository.save(categoriaDespesa);
                            }
                        } catch (CategoriaInvalidaException e) {
                            log.log(Level.SEVERE, "Erro ao cadastrar categoria já existente. ", e);
                            throw e;
                        }

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

    @Override
    public void editarCategoria(CategoriaDTO data) {
        try {
            gerenciadorCategoria.validarEntrada(data.getId());

            boolean validaCategoria = gerenciadorCategoria.verificaCamposObrigatorios(data);

            if(validaCategoria) {
                switch(data.getTipoCategoria()) {
                    case "Receita":
                        try {
                            boolean verificador = gerenciadorCategoria.verificaExistenciaCategoriaReceita(data.getNomeCategoria());

                            if(verificador) {
                                int receita = categoriaReceitaRepository.editarCategoriaReceita(data.getId(), data.getNomeCategoria());
                            }

                        } catch (CategoriaInvalidaException e) {
                            log.log(Level.SEVERE, "Erro ao cadastrar categoria já existente. ", e);
                            throw e;
                        }
                        break;
                    case "Despesa":
                        try {
                            boolean verificador = gerenciadorCategoria.verificaExistenciaCategoriaDespesa(data.getNomeCategoria());

                            if(verificador) {
                                int despesa = categoriaDespesaRepository.editarCategoriaDespesa(data.getId(), data.getNomeCategoria());
                            }

                        } catch (CategoriaInvalidaException e) {
                            log.log(Level.SEVERE, "Erro ao cadastrar categoria já existente. ", e);
                            throw e;
                        }
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

    @Override
    public List<Categorias> buscarTodasCategorias() {
        List<CategoriaDespesa> categoriaDespesas = categoriaDespesaRepository.buscarTodasAsCategoriaDespesa();
        List<CategoriaReceita> categoriaReceitas = categoriaReceitaRepository.buscarTodasAsCategoriaReceita();
        Map<Integer, String> mapaCategorias = new HashMap<>();
        List<Categorias> categorias = new ArrayList<>(); // Inicializando a lista de categorias

        int contador = 0;

        for(int i = 0; i < categoriaReceitas.size(); i++) {
            Categorias categoria = new Categorias(contador, categoriaReceitas.get(i).getNome(), "Receita");
            categorias.add(categoria);
            //System.out.println("receita: " +categoriaReceitas.get(i).getNome()+" id: "+contador);
            contador++;
        }


        //mapaCategorias.put(i, categoriaReceitas.get(i).getNome());


        /*categoriaReceitas.forEach((indice) -> {
            System.out.println("Índice: " + indice + ", nome: " + categoriaReceitas.get(indice.getId()).getNome());
        });*/

        for(int i = 0; i<categoriaDespesas.size(); i++) {
            Categorias categoria = new Categorias(contador, categoriaDespesas.get(i).getNome(), "Despesa");
            categorias.add(categoria);
            mapaCategorias.put(contador, categoriaDespesas.get(i).getNome());
            System.out.println("despesa: " +categoriaDespesas.get(i).getNome()+" id: "+contador);
            contador++;
        }

        /*for(Map.Entry<Integer, String> entry : mapaCategorias.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Nome: " + entry.getValue());
        }*/

        return categorias;
    }

}