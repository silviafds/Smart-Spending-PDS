package com.smartSpd.smartSpding.Core.Classes;

public class DadosBancarios {

    private int id;
    private String nome;

    public DadosBancarios(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
