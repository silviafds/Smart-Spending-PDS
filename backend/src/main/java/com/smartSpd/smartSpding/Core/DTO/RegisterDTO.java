package com.smartSpd.smartSpding.Core.DTO;

import com.smartSpd.smartSpding.Core.Dominio.Role;

import java.sql.Date;


public record RegisterDTO(String nome, String sobrenome, String login, Date datanascimento, String email, String password, Role role) {
}
