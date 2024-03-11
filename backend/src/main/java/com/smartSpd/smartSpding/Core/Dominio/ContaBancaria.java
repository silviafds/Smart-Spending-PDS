package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "conta_bancaria")
@Entity(name = "conta_bancaria")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeBanco;
    private String tipoConta;
    private String agencia;
    private String numeroConta;

    public ContaBancaria(Long id, String nomeBanco, String tipoConta, String agencia, String numeroConta) {
        this.id = id;
        this.nomeBanco = nomeBanco;
        this.tipoConta = tipoConta;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

}
