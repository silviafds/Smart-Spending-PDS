package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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
        BigDecimal big = formataMoeda(data.getValorProjeto());

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
            receita.setValorReceita(big);
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

    public BigDecimal formataMoeda(String numero) {
        String stringNumerica = numero.replaceAll("[^0-9,]", "");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        BigDecimal numeros;
        try {
            numeros = new BigDecimal(decimalFormat.parse(stringNumerica).toString());
        } catch (Exception e) {
            System.out.println("Erro ao converter número: " + e.getMessage());
            return null;
        }

        System.out.println("Número converttido: " + numeros+" | numero entrada: "+stringNumerica);
        return numeros;
    }

    private boolean camposObrigatoriosNaoNulos(ReceitaDTO data) {
        return data.getContaInterna() != null && data.getCategoria() != null &&
                data.getTitulo_contabil() != null && data.getOrigem() != null &&
                data.getValorProjeto() != null && data.getDataReceita() != null &&
                data.getDescricao() != null && data.getPagador() != null;
    }

    public void validarEntrada(ReceitaDTO data) {
        if (data.getId() == null) {
            log.severe("Tentativa de editar receita sem ID.");
            throw new NullPointerException("ID da receita ao editar não pode ser nulo.");
        }
    }

    public boolean validarCamposObrigatorios(ReceitaDTO data)  {
        if (data.getOrigem() == null || data.getOrigem().isEmpty()) {
            return false;
        } else if (data.getOrigem().equals(PIX.toString()) || data.getOrigem().equals(TRANSFERENCIA.toString())) {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                    data.getDataReceita() == null || data.getValorProjeto() == null || data.getPagador() == null ||
                    data.getOrigem() == null || data.getDescricao() == null || data.getContaInterna().equals("") ||
                    data.getCategoria().equals("") || data.getTitulo_contabil().equals("") ||
                    data.getPagador().equals("") || data.getOrigem().equals("") || data.getDescricao().equals("")) {
                return false;
            }
        } else {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                    data.getDataReceita() == null || data.getValorProjeto() == null ||
                    data.getPagador() == null || data.getOrigem() == null || data.getDescricao() == null ||
                    data.getContaInterna().equals("") || data.getCategoria().equals("") || data.getTitulo_contabil().equals("") ||
                    data.getPagador().equals("") || data.getOrigem().equals("") || data.getDescricao().equals("")) {
                return false;
            }
        }

        return true;
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