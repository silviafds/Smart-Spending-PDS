package com.smartSpd.smartSpding.Core.Classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity(name = "balanco")
@Table(name = "balanco")
@Getter
@Setter
@NoArgsConstructor
public class Balanco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo_balanco")
    private String tipoBalanco;

    @Column(name = "analise_balanco")
    private String analise_balanco;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    @Column(name = "tipo_visualizacao")
    private String tipoVisualizacao;

    @Column(name = "categoria_titulo_contabil")
    private String categoriaTituloContabil;

    public Balanco(String nome, String tipoBalanco, String analise_balanco, LocalDate dataInicio, LocalDate dataTermino,
                   String tipoVisualizacao, String categoriaTituloContabil) {
        this.nome = nome;
        this.tipoBalanco = tipoBalanco;
        this.analise_balanco = analise_balanco;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.tipoVisualizacao = tipoVisualizacao;
        this.categoriaTituloContabil = categoriaTituloContabil;
    }
}
