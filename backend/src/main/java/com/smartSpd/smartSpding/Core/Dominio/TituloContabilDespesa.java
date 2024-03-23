package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Table(name = "titulos_contabeis_despesa")
@Entity(name = "titulos_contabeis_despesa")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class TituloContabilDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_despesa_id")
    private CategoriaDespesa categoriaDespesa;

    public TituloContabilDespesa(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }

}
