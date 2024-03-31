package com.smartSpd.smartSpding.Core.Excecao;

public class Excecoes extends Exception {

    public Excecoes(String mensagem) {
        super(mensagem);
    }

    public static void validarCampoNuloOuVazio(String valor, String mensagem) throws Excecoes {
        if (valor == null || valor.isEmpty()) {
            throw new Excecoes(mensagem);
        }
    }

    public static void validarValorNegativo(double valor, String mensagem) throws Excecoes {
        if (valor < 0) {
            throw new Excecoes(mensagem);
        }
    }

    // Outros métodos de exceção podem ser adicionados conforme necessário
}
