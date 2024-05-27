package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "origem")
@Entity(name = "origem")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Origem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    public Origem(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}