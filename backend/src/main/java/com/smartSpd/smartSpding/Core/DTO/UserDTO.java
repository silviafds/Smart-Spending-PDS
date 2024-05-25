package com.smartSpd.smartSpding.Core.DTO;

import java.time.LocalDate;

public class UserDTO {
    private String nome;
    private String sobrenome;
    private String login;
    private LocalDate datanascimento;
    private String email;
    private String password;
    private String role;

    public UserDTO(String nome, String sobrenome, String login, LocalDate datanascimento,
                   String email, String password, String role) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.datanascimento = datanascimento;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(LocalDate datanascimento) {
        this.datanascimento = datanascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
