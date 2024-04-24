package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorDespesa;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaService;
import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.*;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public DespesaServiceImpl(GerenciadorDespesa gerenciadorDespesa, DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
        this.gerenciadorDespesa = gerenciadorDespesa;
    }

    @Override
    public Boolean cadastrarDespesa(DespesaDTO data) throws Excecoes {
        try {
            gerenciadorDespesa.validarCamposObrigatorios(data);
            if (data.getId() == null) {
                String[] dadosReformulados = new String[0];

                if (data.getCategoriaTransacao().equals(PIX.getMeiosPagamento()) || data.getCategoriaTransacao().equals(TRANSFERENCIA.getMeiosPagamento())) {
                    dadosReformulados = gerenciadorDespesa.reformulaDadosBancarios(data.getDadosBancariosOrigem());
                }
                Despesa despesa = gerenciadorDespesa.mapeiaDTOparaDespesa(data, dadosReformulados);
                despesaRepository.save(despesa);
                return true;
            }
        } catch (Excecoes e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova despesa no service. ", e);
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean editarDespesa(DespesaDTO data) {
        try {
            gerenciadorDespesa.validarEntrada(data);

            gerenciadorDespesa.validarCamposObrigatorios(data);

            gerenciadorDespesa.ajustarOrigem(data);

            String[] dadosReformulados = gerenciadorDespesa.reformulaDadosBancarios(data.getDadosBancariosOrigem());

            despesaRepository.editarDespesa(data, dadosReformulados);

            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar despesa no service.", e);
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean deletarDespesa(Long id) {
        try {
            gerenciadorDespesa.validarId(id);
            Optional<Despesa> despesaOptional = despesaRepository.findById(id);
            if (despesaOptional.isPresent()) {
                despesaRepository.delete(despesaOptional.get());
                return true;
            }

            return false;
        } catch (IllegalArgumentException e) {
            log.warning("Id de despesa está nulo.");
            return false;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar despesa no service.", e);
            return false;
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
    public List<TituloContabilDespesa> buscarTodosTitulosContabeisDespesa(Integer id) {
        if(id != null) {
            return despesaRepository.findByAllTitulosContabeisDespesa(id);
        }
        throw new NullPointerException("Id é null.");
    }
}