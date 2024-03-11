package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "titulos_contabeis_receita")
@Entity(name = "titulos_contabeis_receita")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class TituloContabilReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_receita_id")
    private CategoriaReceita categoriaReceita;

    public TituloContabilReceita(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public TituloContabilReceita(String nome) {
    }

}
