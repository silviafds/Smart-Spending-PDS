package com.smartSpd.smartSpding.Core.Classes;

import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import java.time.LocalDate;

@Data
@SqlResultSetMapping(
        name = "BalancoDespesaReceitaMapping",
        classes = @ConstructorResult(
                targetClass = BalancoDespesaReceita.class,
                columns = {
                        @ColumnResult(name = "categoriaOuTituloContabil", type = String.class),
                }
        )
)

@NoArgsConstructor
public class BalancoDespesaReceita extends BalancoDespesa {

    public BalancoDespesaReceita(BalancoDTO balancoDTO) {
        this.configurarAtributosComDTO(balancoDTO);
    }

    @Transient
    private String categoriaOuTituloContabil;

    public BalancoDespesaReceita(String dado, Long valor, String categoriaOuTituloContabil) {
        super(dado, valor);
        this.categoriaOuTituloContabil = categoriaOuTituloContabil;
    }

    public BalancoDespesaReceita(String nome, String tipoBalanco, String analiseBalanco, LocalDate dataInicio, LocalDate dataTermino, String tipoVisualizacao, String categoriaOuTituloContabil) {
        super(nome, tipoBalanco, analiseBalanco, dataInicio, dataTermino, tipoVisualizacao);
        this.categoriaOuTituloContabil = categoriaOuTituloContabil;
    }

    public void configurarAtributosComDTO(BalancoDTO balancoDTO) {
        this.setNome(balancoDTO.getNome());
        this.setTipoBalanco(balancoDTO.getTipoBalanco());
        this.setAnaliseBalanco(balancoDTO.getAnaliseBalanco());
        this.setDataInicio(balancoDTO.getDataInicio());
        this.setDataTermino(balancoDTO.getDataTermino());
        this.setTipoVisualizacao(balancoDTO.getTipoVisualizacao());
        this.setCategoriaOuTituloContabil(balancoDTO.getCategoriaOuTituloContabil());
    }
}
