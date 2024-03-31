package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Excecao.Excecoes;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.PIX;
import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.TRANSFERENCIA;

@Component
public class GerenciadorReceita {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    public String[] reformulaDadosBancarios(String dadosBancariosDestino) {
        System.out.println("entrei em reformula Dados Bancarios: "+dadosBancariosDestino);
        if (dadosBancariosDestino != null) {
            return dadosBancariosDestino.split("/");
        } else {
            throw new IllegalArgumentException("Os dados bancários de destino não podem ser nulos.");
        }
    }

    public Receita mapeiaDTOparaReceita(ReceitaDTO data, String[] dadosReformulados) {
        Receita receita = new Receita();
        if(data.getOrigem().equals("Pix") || data.getOrigem().equals("Transferência")) {
            receita.setTipoContaDestino(dadosReformulados[0]);
            receita.setAgenciaDestino(dadosReformulados[1]);
            receita.setNumeroContaDestino(dadosReformulados[2]);
        } else {
            receita.setTipoContaDestino("");
            receita.setAgenciaDestino("");
            receita.setNumeroContaDestino("");
        }
        if(camposObrigatoriosNaoNulos(data)) {
            receita.setCategoria(data.getCategoria());
            receita.setTitulo_contabil(data.getTitulo_contabil());
            receita.setDataReceita(data.getDataReceita());
            receita.setValorReceita(data.getValorReceita());
            receita.setOrigem(data.getOrigem());
            receita.setPagador(data.getPagador());
            receita.setBancoOrigem(data.getBancoOrigem());
            receita.setAgenciaOrigem(data.getAgenciaOrigem());
            receita.setNumeroContaOrigem(data.getNumeroContaOrigem());
            receita.setBancoDestino(data.getBancoDestino());
            receita.setDescricao(data.getDescricao());
            receita.setContaInterna(data.getContaInterna());
            return receita;
        } else {
            throw new IllegalArgumentException("Dados de receita estão nulos.");
        }
    }

    private boolean camposObrigatoriosNaoNulos(ReceitaDTO data) {
        return data.getContaInterna() != null && data.getCategoria() != null &&
                data.getTitulo_contabil() != null && data.getOrigem() != null &&
                data.getValorReceita() != null && data.getDataReceita() != null &&
                data.getDescricao() != null && data.getPagador() != null;
    }

    public void validarEntrada(ReceitaDTO data) {
        if (data.getId() == null) {
            log.severe("Tentativa de editar receita sem ID.");
            throw new NullPointerException("ID da receita ao editar não pode ser nulo.");
        }
    }

    public void validarCamposObrigatorios(ReceitaDTO data) throws Excecoes {
        if (data.getOrigem() == null || data.getOrigem().isEmpty()) {
            log.severe("Há algo de errado com a origem.");
            Excecoes.validarCampoNuloOuVazio(data.getOrigem(),"O campo de categoria de transação está vazio ou nulo");
        } else if (data.getOrigem().equals(PIX.toString()) || data.getOrigem().equals(TRANSFERENCIA.toString())) {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                    data.getDataReceita() == null || data.getValorReceita() <= 0 || data.getPagador() == null ||
                    data.getOrigem() == null || data.getDescricao() == null) {
                log.severe("Um ou mais campos obrigatórios estão vazios.");
                Excecoes.validarCampoNuloOuVazio(data.getOrigem(),"Um ou mais campos obrigatórios estão vazios.");
            }
        } else {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                    data.getDataReceita() == null || data.getValorReceita() <= 0 || data.getBancoOrigem() == null ||
                    data.getDadosBancariosDestino() == null || data.getPagador() == null || data.getOrigem() == null ||
                    data.getDescricao() == null) {
                log.severe("Um ou mais campos obrigatórios estão vazios.");
                Excecoes.validarCampoNuloOuVazio(data.getOrigem(),"Um ou mais campos obrigatórios estão vazios.");
            }
        }
    }

    public void ajustarOrigem(ReceitaDTO data) {
        if ("Pix".equals(data.getOrigem()) || "Papel e moeda".equals(data.getOrigem())) {
            data.setAgenciaOrigem("");
            data.setNumeroContaOrigem("");
        }
    }

    public void validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID de receita está nulo.");
        }
    }



}
