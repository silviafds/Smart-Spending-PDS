package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorReceita;
import com.smartSpd.smartSpding.Core.CasoUso.ReceitaService;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.PIX;
import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.TRANSFERENCIA;

@Component
public class ReceitaServiceImpl implements ReceitaService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ReceitaRepository receitaRepository;

    @Autowired
    private GerenciadorReceita gerenciadorReceita;

    public ReceitaServiceImpl(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }


    @Transactional
    @Override
    public Boolean cadastrarReceita(ReceitaDTO data) throws Excecoes {
        try {
            gerenciadorReceita.validarCamposObrigatorios(data);
            if (data.getId() == null) {
                String[] dadosReformulados = new String[0];
                if(data.getOrigem().equals(PIX.getMeiosPagamento()) || data.getOrigem().equals(TRANSFERENCIA.getMeiosPagamento())) {
                    dadosReformulados = gerenciadorReceita.reformulaDadosBancarios(data.getDadosBancariosDestino());
                }
                Receita receita = gerenciadorReceita.mapeiaDTOparaReceita(data, dadosReformulados);
                receitaRepository.save(receita);
                return true;
            }

        } catch (Excecoes e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar nova receita no service. ", e);
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean editarReceita(ReceitaDTO data) {
        try {
            gerenciadorReceita.validarEntrada(data);

            gerenciadorReceita.validarCamposObrigatorios(data);

            gerenciadorReceita.ajustarOrigem(data);

            String[] dadosReformulados = gerenciadorReceita.reformulaDadosBancarios(data.getDadosBancariosDestino());

            receitaRepository.editarReceita(data, dadosReformulados);

            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar receita no service.", e);
            return false;
        }
    }

    @Transactional
    @Override
    public Boolean deletarReceita(Long id) {
        try {
            gerenciadorReceita.validarId(id);
            Optional<Receita> receitaOptional = receitaRepository.findById(id);
            if (receitaOptional.isPresent()) {
                receitaRepository.delete(receitaOptional.get());
                return true;
            }

            return false;
        } catch (IllegalArgumentException e) {
            log.warning("Id de receita está nulo.");
            return false;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar receita no service.", e);
            return false;
        }
    }

    @Override
    public List<CategoriaReceita> buscarTodasCategoriasReceitas() {
        return receitaRepository.buscarTodasAsCategoriaReceita();
    }

    @Override
    public List<Receita> buscarTodasAsReceitas() {
        try {
            List<Receita> receitas = receitaRepository.buscarTodasReceitas();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            receitas.forEach(receita -> {
                LocalDate dataReceita = receita.getDataReceita();
                String dataFormatada = dataReceita.format(formatter);
                receita.setDataFormatada(dataFormatada);
            });

            return receitas;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todas as receitas no service.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Receita> buscarReceitasPorId(Integer id) {
        if(id != null) {
            List<Receita> lista = receitaRepository.buscarReceitaPorId(id);
            String tipoConta;
            String agencia;
            String conta;
            String dadosBancarios;
            for(Receita receita : lista) {
                tipoConta = receita.getTipoContaDestino();
                agencia = receita.getAgenciaDestino();
                conta = receita.getNumeroContaDestino();
                dadosBancarios = tipoConta + "/" + agencia + "/" + conta;
                receita.setDadosBancarios(dadosBancarios);
            }
            return receitaRepository.buscarReceitaPorId(id);
        }
        throw new NullPointerException("Id é null.");
    }

    @Override
    public List<TituloContabilReceita>buscarTodosTitulosContabeisReceitas(Integer id) {
        if(id != null) {
            return receitaRepository.findByAllTitulosContabeisReceita(id);
        }
        throw new NullPointerException("Id é null.");
    }

}