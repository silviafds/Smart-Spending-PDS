package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Despesa;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.logging.Logger;

@Component
public class GerenciadorDespesa {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    public String[] reformulaDadosBancarios(String dadosBancariosOrigem, String tipoTransacao) {
        if(Objects.equals(tipoTransacao, "Pix") || Objects.equals(tipoTransacao, "Transferência")) {
            if (dadosBancariosOrigem != null) {
                return dadosBancariosOrigem.split("/");
            } else {
                throw new NullPointerException("Os dados bancários de destino não podem ser nulos.");
            }
        }
        return new String[]{};
    }

    public Despesa mapeiaDTOparaDespesa(DespesaDTO data, String[] dadosReformulados) {
        Despesa despesa = new Despesa();
        try {
            if(dadosReformulados == null ||dadosReformulados.length == 0) {
                despesa.setTipoContaOrigem("");
                despesa.setAgenciaOrigem("");
                despesa.setNumeroContaOrigem("");
            } else {
                despesa.setTipoContaOrigem(dadosReformulados[0]);
                despesa.setAgenciaOrigem(dadosReformulados[1]);
                despesa.setNumeroContaOrigem(dadosReformulados[2]);
            }

            if(camposObrigatoriosNaoNulos(data)) {
                despesa.setCategoria(data.getCategoria());
                despesa.setTitulo_contabil(data.getTitulo_contabil());
                despesa.setDataDespesa(data.getDataDespesa());
                despesa.setValorDespesa(data.getValorDespesa());
                despesa.setCategoriaTransacao(data.getCategoriaTransacao());
                despesa.setBancoOrigem(data.getBancoOrigem());
                despesa.setBeneficiario(data.getBeneficiario());
                despesa.setBancoDestino(data.getBancoDestino());
                despesa.setAgenciaDestino(data.getAgenciaDestino());
                despesa.setNumeroContaDestino(data.getNumeroContaDestino());
                despesa.setDescricao(data.getDescricao());
                despesa.setContaInterna(data.getContaInterna());
            }
        } catch (NullPointerException e) {
            log.severe("Dados de despesa estão nulos.");
            throw new IllegalArgumentException("Dados de despesa estão nulos.");
        }
        return despesa;
    }

    private boolean camposObrigatoriosNaoNulos(DespesaDTO data) {
        if(Objects.equals(data.getCategoriaTransacao(), "Pix") || Objects.equals(data.getCategoriaTransacao(), "Transferência")) {
            return data.getContaInterna() != null && data.getCategoria() != null &&
                    data.getTitulo_contabil() != null && data.getCategoriaTransacao() != null &&
                    data.getValorDespesa() != null && data.getDataDespesa() != null &&
                    data.getDescricao() != null && data.getDadosBancariosOrigem() != null &&
                    data.getBeneficiario() != null;
        }
        return data.getContaInterna() != null && data.getCategoria() != null &&
                data.getTitulo_contabil() != null && data.getCategoriaTransacao() != null &&
                data.getValorDespesa() != null && data.getDataDespesa() != null &&
                data.getDescricao() != null && data.getBeneficiario() != null;
    }

    public void validarEntrada(DespesaDTO data) {
        if (data.getId() == null) {
            log.severe("Tentativa de editar despesa sem ID.");
            throw new NullPointerException("ID da despesa ao editar não pode ser nulo.");
        }
    }

    public void validarCamposObrigatorios(DespesaDTO data) {
        if (data.getContaInterna() == null ||
                data.getCategoria() == null ||
                data.getTitulo_contabil() == null ||
                data.getBeneficiario() == null ||
                data.getCategoriaTransacao() == null ||
                data.getDescricao() == null ||
                data.getDataDespesa() == null ||
                data.getValorDespesa() <= 0) {
            log.severe("Um ou mais campos obrigatórios estão vazios.");
            throw new IllegalArgumentException("Campos obrigatórios não podem ser nulos ou vazios.");
        }
    }

    public void ajustarOrigem(DespesaDTO data) {
        if ("Pix".equals(data.getCategoriaTransacao()) || "Papel e moeda".equals(data.getCategoriaTransacao())) {
            data.setAgenciaDestino("");
            data.setNumeroContaDestino("");
        }
    }

    public void validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID de despesa está nulo.");
        }
    }



}
