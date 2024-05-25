package com.smartSpd.smartSpding.Aplicacao.Gerenciador;

import com.smartSpd.smartSpding.Core.DTO.DespesaDTO;
import com.smartSpd.smartSpding.Core.Dominio.Despesa;
import com.smartSpd.smartSpding.Core.Dominio.Projetos;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ProjetosRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.PIX;
import static com.smartSpd.smartSpding.Core.Enum.MetodosPagamento.TRANSFERENCIA;

@Component
public class GerenciadorDespesa {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ProjetosRepository projetosRepository;

    public GerenciadorDespesa(ProjetosRepository projetosRepository) {
        this.projetosRepository = projetosRepository;
    }

    public String[] reformulaDadosBancarios(String dadosBancariosOrigem) {
        if (!Objects.equals(dadosBancariosOrigem, "")) {
            return dadosBancariosOrigem.split("/");
        } else {
            throw new NullPointerException("Os dados bancários de destino não podem ser nulos.");
        }
    }

    public Despesa mapeiaDTOparaDespesa(DespesaDTO data, String[] dadosReformulados) {
        Despesa despesa = new Despesa();
        BigDecimal big = formataMoeda(data.getValorProjeto());

        try {
            if(dadosReformulados == null ||dadosReformulados.length == 0) {
                despesa.setTipoContaOrigem("");
                despesa.setAgenciaOrigem("");
                despesa.setNumeroContaOrigem("");
                despesa.setBancoOrigem("");
            } else {
                despesa.setBancoOrigem(data.getBancoOrigem());
                despesa.setTipoContaOrigem(dadosReformulados[0]);
                despesa.setAgenciaOrigem(dadosReformulados[1]);
                despesa.setNumeroContaOrigem(dadosReformulados[2]);
            }
            if(camposObrigatoriosNaoNulos(data)) {
                despesa.setCategoria(data.getCategoria());
                despesa.setTitulo_contabil(data.getTitulo_contabil());
                despesa.setDataDespesa(data.getDataDespesa());
                despesa.setValorDespesa(big);
                despesa.setCategoriaTransacao(data.getCategoriaTransacao());
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

    private BigDecimal formataMoeda(String numero) {
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

    private static String substituirPontoPorVirgula(String numeroComPonto) {
        return numeroComPonto.replace('.', ',');
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

    public boolean validarCamposObrigatorios(DespesaDTO data) {
        if (data.getCategoriaTransacao() == null || data.getCategoriaTransacao().isEmpty()) {
            return false;
        } else if (data.getCategoriaTransacao().equals(PIX.toString()) || data.getCategoriaTransacao().equals(TRANSFERENCIA.toString())) {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                data.getDataDespesa() == null || data.getValorDespesa() <= 0 || data.getBeneficiario() == null ||
                data.getCategoriaTransacao().equals("") || data.getDescricao().equals("") || data.getContaInterna().equals("") ||
                data.getCategoria().equals("") || data.getTitulo_contabil().equals("") || data.getValorDespesa() <= 0 ||
                data.getBeneficiario().equals("") || data.getCategoriaTransacao().equals("") || data.getDescricao().equals("")) {
                return false;
            }
        } else {
            if (data.getContaInterna() == null || data.getCategoria() == null || data.getTitulo_contabil() == null ||
                data.getDataDespesa() == null || data.getValorDespesa() <= 0 || data.getBeneficiario() == null ||
                data.getCategoriaTransacao() == null || data.getDescricao() == null || data.getContaInterna().equals("") ||
                data.getCategoria().equals("") || data.getTitulo_contabil().equals("") || data.getValorDespesa().equals("") ||
                data.getBeneficiario().equals("") || data.getCategoriaTransacao().equals("") || data.getDescricao().equals("")) {
                return false;
            }
        }

        return true;
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

    public void verificaCategoriaProjeto(DespesaDTO data) {

        List<Projetos> projeto = projetosRepository.buscarProjetoPorID(data.getIdentificadorProjeto());

        if(!projeto.isEmpty()) {
            Double valorAntigo = 0.0;
            Projetos projetoEncontrado = projeto.get(0);
            if(projetoEncontrado.getValor_arrecadado_atual()!=null) {
                valorAntigo = Double.valueOf(projetoEncontrado.getValor_arrecadado_atual());
            }

            Double novoValor = valorAntigo + data.getValorDespesa();
            projetoEncontrado.setValor_arrecadado_atual(String.valueOf(novoValor));

            projetosRepository.save(projetoEncontrado);

        }

    }

}