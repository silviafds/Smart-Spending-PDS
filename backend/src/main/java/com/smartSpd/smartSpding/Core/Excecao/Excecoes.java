package com.smartSpd.smartSpding.Core.Excecao;

public class Excecoes extends Exception {

    public Excecoes(String mensagem) {
        super(mensagem);
    }

    public static boolean validarCampoNuloOuVazio(String valor, String mensagem) throws Excecoes {
        if (valor == null || valor.isEmpty()) {
        	 return true;
        }
        return false;
    }

    public static boolean validarValorNegativo(double valor, String mensagem) throws Excecoes {
        if (valor < 0) {
            return true;
        }
        return false;
    }

    // Outros métodos de exceção podem ser adicionados conforme necessário
}
