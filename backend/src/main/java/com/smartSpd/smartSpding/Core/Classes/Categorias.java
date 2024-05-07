package com.smartSpd.smartSpding.Core.Classes;

public class Categorias {
    private int id;
    private String nome;
    private String tipo;

    public Categorias(int id, String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public Categorias() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}