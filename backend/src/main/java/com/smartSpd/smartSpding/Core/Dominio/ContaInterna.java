package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "ContaInterna")
@Entity(name = "ContaInterna")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class ContaInterna  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean desabilitarConta;

    public ContaInterna(Long id, String nome, Boolean desabilitarConta){
        this.id = id;
        this.nome = nome;
        this.desabilitarConta = desabilitarConta;
    }

    public ContaInterna(String nome) {
    }

}
