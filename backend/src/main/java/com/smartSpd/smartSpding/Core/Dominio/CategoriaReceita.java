package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Table(name = "categoria_receita")
@Entity(name = "categoria_receita")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class CategoriaReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @OneToMany(mappedBy = "categoriaReceita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TituloContabilReceita> titulosContabeis;

    public CategoriaReceita(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

}