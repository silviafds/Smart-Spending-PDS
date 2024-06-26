package com.smartSpd.smartSpding.Core.Dominio;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "balanco_id")
    private List<CategoriaDespesa> categorias = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "balanco_id")
    private List<TituloContabilDespesa> titulosContabeis = new ArrayList<>();

    private String tipoEstabelecimento;

    public Balancos(Long id, String nome, String tipoBalanco, String analise_balanco, LocalDate data_inicio,
                    LocalDate data_termino, String tipo_visualizacao, String categoria_titulo_contabil,
                    boolean dashboard_check, String tipoEstabelecimento) {
        this.id = id;
        this.nome = nome;
        this.tipoBalanco = tipoBalanco;
        this.analise_balanco = analise_balanco;
        this.data_inicio = data_inicio;
        this.data_termino = data_termino;
        this.tipo_visualizacao = tipo_visualizacao;
        this.categoria_titulo_contabil = categoria_titulo_contabil;
        this.dashboard_check = dashboard_check;
        this.categorias = new ArrayList<>();
        this.titulosContabeis = new ArrayList<>();
        this.tipoEstabelecimento = tipoEstabelecimento;
    }

    public void adicionaCategoria(CategoriaDespesa categoria) {
        this.categorias.add(categoria);
    }

    public void adicionaTituloContabil(TituloContabilDespesa tituloContabil) {
        this.titulosContabeis.add(tituloContabil);
    }
}