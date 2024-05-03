package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.Gerenciador.GerenciadorDespesaReceita;
import com.smartSpd.smartSpding.Core.CasoUso.DespesaReceitaBalancoService;
import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DespesaReceitaBalancoServiceImpl implements DespesaReceitaBalancoService {

    @Autowired
    private GerenciadorDespesaReceita gerenciadorDespesaReceita;


    public DespesaReceitaBalancoServiceImpl(GerenciadorDespesaReceita gerenciadorDespesaReceita) {
        this.gerenciadorDespesaReceita = gerenciadorDespesaReceita;
    }

    @Override
    public List<BalancoDespesaReceita> buscarDadosReceitaDespesa(BalancoDTO balancoRapidoDTO) {
        String sql;
        List<BalancoDespesaReceita> resultados = new ArrayList<>();

        gerenciadorDespesaReceita.verificaDTOIsNULL(balancoRapidoDTO);

        sql = gerenciadorDespesaReceita.queryBalanco(balancoRapidoDTO);
        resultados.addAll(gerenciadorDespesaReceita.montaQuery(sql, balancoRapidoDTO));

        return resultados;
    }

    @Override
    public void salvarDadosBalanco(BalancoDTO balancoDTO) {
        BalancoDespesaReceita balancoDespesaReceita = new BalancoDespesaReceita();

        balancoDespesaReceita.setNome(balancoDTO.getNome());
        balancoDespesaReceita.setTipoBalanco(balancoDTO.getTipoBalanco());
        balancoDespesaReceita.setAnaliseBalanco(balancoDTO.getAnaliseBalanco());
        balancoDespesaReceita.setDataInicio(balancoDTO.getDataInicio());
        balancoDespesaReceita.setDataTermino(balancoDTO.getDataTermino());
        balancoDespesaReceita.setTipoVisualizacao(balancoDTO.getTipoVisualizacao());
        balancoDespesaReceita.setCategoriaOuTituloContabil(balancoDTO.getCategoriaOuTituloContabil());
    }

}
