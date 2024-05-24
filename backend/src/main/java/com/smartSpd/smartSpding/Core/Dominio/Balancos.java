package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Setter
@Table(name = "balancos")
@Entity(name = "balancos")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Component
public class Balancos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipoBalanco;
    private String analise_balanco;
    private LocalDate data_inicio;
    private LocalDate data_termino;
    private String tipo_visualizacao;
    private String categoria_titulo_contabil;
    private boolean dashboard_check;

    @Transient
    private String data_inicio_balanco;

    @Transient
    private String data_final_balanco;

    public Balancos(Long id, String nome, String tipoBalanco, String analise_balanco, LocalDate data_inicio,
                    LocalDate data_termino, String tipo_visualizacao, String categoria_titulo_contabil,
                    boolean dashboard_check) {
        this.id = id;
        this.nome = nome;
        this.tipoBalanco = tipoBalanco;
        this.analise_balanco = analise_balanco;
        this.data_inicio = data_inicio;
        this.data_termino = data_termino;
        this.tipo_visualizacao = tipo_visualizacao;
        this.categoria_titulo_contabil = categoria_titulo_contabil;
        this.dashboard_check = dashboard_check;
    }

}