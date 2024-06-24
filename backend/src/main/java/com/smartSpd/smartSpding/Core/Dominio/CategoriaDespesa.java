package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private String contexto;

    @OneToMany(mappedBy = "categoriaDespesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TituloContabilDespesa> titulosContabeis = new ArrayList<>();

    public CategoriaDespesa(String nome, String contexto){
        this.nome = nome;
        this.contexto = contexto;
        this.titulosContabeis = new ArrayList<>();
    }

    public void adicionarTituloContabil(TituloContabilDespesa tituloContabil) {
        this.titulosContabeis.add(tituloContabil);
        tituloContabil.setCategoriaDespesa(this);
    }

    public void removerTituloContabil(TituloContabilDespesa tituloContabil) {
        this.titulosContabeis.remove(tituloContabil);
        tituloContabil.setCategoriaDespesa(null);
    }
}