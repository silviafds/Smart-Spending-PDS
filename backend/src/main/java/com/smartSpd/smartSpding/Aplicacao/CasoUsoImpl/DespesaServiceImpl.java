package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorDespesa;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaService;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Core.Excecao.DespesaInvalidaException;
import com.smartSpd.smartSpding.Core.Excecao.DespesaNaoEncontradaException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ProjetosRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.*;

@Component
public class DespesaServiceImpl implements DespesaService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));
    private final DespesaRepository despesaRepository;
    private final GerenciadorDespesa gerenciadorDespesa;
    private final ProjetosRepository projetosRepository;

    public DespesaServiceImpl(GerenciadorDespesa gerenciadorDespesa, DespesaRepository despesaRepository,
                              ProjetosRepository projetosRepository) {
        this.despesaRepository = despesaRepository;
        this.gerenciadorDespesa = gerenciadorDespesa;
        this.projetosRepository = projetosRepository;
    }

    @Override
    public void cadastrarDespesa(DespesaDTO data) {
        try {
            boolean validacao = gerenciadorDespesa.validarCamposObrigatorios(data);
            if(validacao) {
                if(data.getIdentificadorProjeto()!=null) {
                    gerenciadorDespesa.verificaCategoriaProjeto(data);
                }

                if(data.getId() == null) {
                    String[] dadosReformulados = new String[0];

                    if (data.getCategoriaTransacao().equals(PIX.getMeiosPagamento()) || data.getCategoriaTransacao().equals(TRANSFERENCIA.getMeiosPagamento())) {
                        dadosReformulados = gerenciadorDespesa.reformulaDadosBancarios(data.getDadosBancariosOrigem());
                    }
                    Despesa despesa = gerenciadorDespesa.mapeiaDTOparaDespesa(data, dadosReformulados);

                    despesaRepository.save(despesa);
                }

            } else {
                throw new DespesaInvalidaException("Campos obrigatórios da despesa não foram preenchidos.");
            }
        } catch (DespesaInvalidaException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova despesa no service. ", e);
            throw e;
        }
    }

   // @Override
    public void editarDespesa(DespesaDTO data) throws Exception {
        try {
            gerenciadorDespesa.validarEntrada(data);

            boolean validacao = gerenciadorDespesa.validarCamposObrigatorios(data);
            if(validacao) {
                gerenciadorDespesa.ajustarOrigem(data);

                String[] dadosReformulados = new String[0];

                if (data.getCategoriaTransacao().equals(PIX.getMeiosPagamento()) || data.getCategoriaTransacao().equals(TRANSFERENCIA.getMeiosPagamento())) {
                    dadosReformulados = gerenciadorDespesa.reformulaDadosBancarios(data.getDadosBancariosOrigem());
                }

                despesaRepository.editarDespesa(data, dadosReformulados);
            } else {
                throw new DespesaInvalidaException("Campos obrigatórios da despesa não foram preenchidos.");
            }
        } catch (DespesaInvalidaException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova despesa no service. ", e);
            throw e;
        }
    }

    @Override
    public void deletarDespesa(Long id) {
        try {
            gerenciadorDespesa.validarId(id);

            Optional<Despesa> despesaOptional = despesaRepository.findById(id);

            if (!despesaOptional.isPresent()) {
                throw new DespesaNaoEncontradaException("Despesa com ID " + id + " não encontrada.");
            }

            despesaRepository.delete(despesaOptional.get());
        } catch (IllegalArgumentException e) {
            log.warning("Id de despesa está nulo.");
            throw new IllegalArgumentException("Id de despesa inválido.", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar despesa no service.", e);
            throw new RuntimeException("Erro ao deletar despesa.", e);
        }
    }

    @Override
    public List<CategoriaDespesa> buscarTodasCategoriasDespesa() {
        return despesaRepository.buscarTodasAsCategoriaDespesa();
    }

    @Override
    public List<Despesa> buscarTodasAsDespesas() {
        try {
            List<Despesa> despesas = despesaRepository.buscarTodasDespesas();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            despesas.forEach(despesa -> {
                LocalDate dataDespesa = despesa.getDataDespesa();
                String dataFormatada = dataDespesa.format(formatter);
                despesa.setDataFormatada(dataFormatada);
            });

            return despesas;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todas as despesas no service.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Despesa> buscarDespesasPorId(Integer id) {
        if(id != null) {
            List<Despesa> lista = despesaRepository.buscarDespesaPorId(id);
            String tipoConta;
            String agencia;
            String conta;
            String dadosBancarios;
            for(Despesa despesa : lista) {
                tipoConta = despesa.getTipoContaOrigem();
                agencia = despesa.getAgenciaOrigem();
                conta = despesa.getNumeroContaOrigem();
                dadosBancarios = tipoConta + "/" + agencia + "/" + conta;
                despesa.setDadosBancarios(dadosBancarios);
            }
            return despesaRepository.buscarDespesaPorId(id);
        }
        throw new NullPointerException("Id é null.");
    }
    @Override
    public List<?> buscarTodosTitulosContabeisDespesa(Integer id) {
        if(id != null) {
            String projeto = despesaRepository.projetosPorCategoria(id);

            if(projeto.equals("Projeto")) {
                return projetosRepository.buscarTodosProjetos();
            }
            return despesaRepository.findByAllTitulosContabeisDespesa(id);
        }
        throw new NullPointerException("Id é null.");
    }
}