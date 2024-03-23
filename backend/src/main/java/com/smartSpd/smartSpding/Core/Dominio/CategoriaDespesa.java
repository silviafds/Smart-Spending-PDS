package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Table(name = "categoria_despesa")
@Entity(name = "categoria_despesa")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class CategoriaDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

    @OneToMany(mappedBy = "categoriaDespesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TituloContabilDespesa> titulosContabeis;

    public CategoriaDespesa(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

}