package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "conselhos")
@Entity(name = "conselhos")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Conselhos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int identificador;
    private boolean status_despesa;
    private String meta_despesa;
    private boolean status_receita;
    private String meta_receita;
    private String tempo_conselho;

    public Conselhos(int identificador, boolean statusDespesa, String metaDespesa, boolean statusReceita,
                     String metaReceita, String tempoConselho) {
        this.identificador = identificador;
        this.status_despesa = statusDespesa;
        this.meta_despesa = metaDespesa;
        this.status_receita = statusReceita;
        this.meta_receita = metaReceita;
        this.tempo_conselho = tempoConselho;
    }
}
