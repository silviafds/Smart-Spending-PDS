package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

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
        if(camposObrigatoriosNaoNulos(data)) {
            Receita receita = new Receita();
            receita.setCategoria(data.getCategoria());
            receita.setTitulo_contabil(data.getTitulo_contabil());
            receita.setDataReceita(data.getDataReceita());
            receita.setValorReceita(data.getValorReceita());
            receita.setOrigem(data.getOrigem());
            receita.setBancoOrigem(data.getBancoOrigem());
            receita.setAgenciaOrigem(data.getAgenciaOrigem());
            receita.setNumeroContaOrigem(data.getNumeroContaOrigem());
            receita.setBancoDestino(data.getBancoDestino());
            receita.setAgenciaDestino(dadosReformulados[1]);
            receita.setNumeroContaDestino(dadosReformulados[2]);
            receita.setDescricao(data.getDescricao());
            receita.setContaInterna(data.getContaInterna());
            receita.setTipoContaDestino(dadosReformulados[0]);
            return receita;
        } else {
            throw new IllegalArgumentException("Dados de receita estão nulos.");
        }
    }

    private boolean camposObrigatoriosNaoNulos(ReceitaDTO data) {
        return data.getContaInterna() != null && data.getCategoria() != null &&
                data.getTitulo_contabil() != null && data.getOrigem() != null &&
                data.getValorReceita() != null && data.getDataReceita() != null &&
                data.getDescricao() != null;
    }

    public void validarEntrada(ReceitaDTO data) {
        if (data.getId() == null) {
            log.severe("Tentativa de editar receita sem ID.");
            throw new NullPointerException("ID da receita ao editar não pode ser nulo.");
        }
    }

    public void validarCamposObrigatorios(ReceitaDTO data) {
        if (data.getContaInterna() == null ||
                data.getCategoria() == null ||
                data.getTitulo_contabil() == null ||
                data.getValorReceita() == null ||
                data.getOrigem() == null ||
                data.getDescricao() == null ||
                data.getDataReceita() == null ||
                data.getValorReceita() <= 0) {
            log.warning("Um ou mais campos obrigatórios estão vazios.");
            throw new IllegalArgumentException("Campos obrigatórios não podem ser nulos ou vazios.");
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
