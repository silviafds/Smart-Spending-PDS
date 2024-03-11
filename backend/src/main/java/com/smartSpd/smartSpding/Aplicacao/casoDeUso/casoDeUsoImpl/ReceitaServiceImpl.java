package com.smartSpd.smartSpding.Aplicacao.casoDeUso.casoDeUsoImpl;

import com.smartSpd.smartSpding.Aplicacao.casoDeUso.ReceitaService;
import com.smartSpd.smartSpding.Core.DTO.EditarReceitaDTO;
import com.smartSpd.smartSpding.Core.DTO.ReceitaDTO;
import com.smartSpd.smartSpding.Core.Dominio.CategoriaReceita;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Core.Dominio.TituloContabilReceita;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ContaInternaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ReceitaServiceImpl implements ReceitaService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ReceitaRepository receitaRepository;

    @Autowired
    private ContaInternaRepository contaInternaRepository;

    public ReceitaServiceImpl(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    private String[] reformulaDadosBancarios(String dadosBancariosDestino) {
        return dadosBancariosDestino.split("/");
    }

    @Transactional
    @Override
    public Boolean cadastrarReceita(ReceitaDTO data) {
        if (data.getId() == null) {
            try {
                String[] dadosReformulados = reformulaDadosBancarios(data.getDadosBancariosDestino());

                Receita receita = new Receita();
                receita.setId(data.getId());
                receita.setCategoria(data.getCategoria());
                receita.setTitulo_contabil(data.getTitulo_contabil());
                receita.setDataReceita(data.getDataReceita());
                receita.setValorReceita(data.getValorReceita());
                receita.setOrigem(data.getOrigem());
                receita.setBancoOrigem(data.getBancoOrigem());
                receita.setAgenciaOrigem(data.getAgenciaOrigem());
                receita.setNumeroContaOrigem(data.getNumeroContaOrigem());
                receita.setBancoDestino(data.getBancoDestino());
                receita.setAgenciaDestino(dadosReformulados[1]);
                receita.setNumeroContaDestino(dadosReformulados[2]);
                receita.setDescricao(data.getDescricao());
                receita.setContaInterna(data.getContaInterna());
                receita.setTipoContaDestino(dadosReformulados[0]);
                receitaRepository.save(receita);
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Erro ao cadastrar nova receita no service. ", e);
            }
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean editarReceita(ReceitaDTO data) {
            String[] dadosReformulados = reformulaDadosBancarios(data.getDadosBancariosDestino());
        if (data.getOrigem().equals("Pix")) {
            data.setAgenciaOrigem("");
            data.setNumeroContaOrigem("");
        }
        receitaRepository.editarReceita(
                data.getId(),
                data.getCategoria(),
                data.getTitulo_contabil(),
                data.getDataReceita(),
                data.getValorReceita(),
                data.getOrigem(),
                data.getBancoOrigem(),
                data.getAgenciaOrigem(),
                data.getNumeroContaOrigem(),
                data.getBancoDestino(),
                dadosReformulados[1],
                dadosReformulados[2],
                data.getDescricao(),
                data.getContaInterna()
        );

        return true;
    }

    @Transactional
    @Override
    public Boolean deletarReceita(EditarReceitaDTO data) {
        Optional<Receita> receitaOptional = receitaRepository.findById(data.id());
        if (receitaOptional.isPresent()) {
            receitaRepository.delete(receitaOptional.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CategoriaReceita> buscarTodasCategoriasReceitas() {
        return receitaRepository.findByAllCategoriaReceita();
    }

    @Override
    public List<Receita> buscarTodasAsReceitas() {
        List<Receita> receitas = receitaRepository.returnAllReceitas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        receitas.forEach(receita -> {
            LocalDate dataReceita = receita.getDataReceita();
            String dataFormatada = dataReceita.format(formatter);
            receita.setDataFormatada(dataFormatada);
        });

        return receitas;
    }

    @Override
    public List<Receita> buscarReceitasPorId(int id) {
        List<Receita> lista = receitaRepository.findReceitaById(id);
        String tipoConta;
        String agencia;
        String conta;
        String dadosBancarios;
        for(Receita receita : lista) {
            tipoConta = receita.getTipoContaDestino();
            agencia = receita.getAgenciaDestino();
            conta = receita.getNumeroContaDestino();
            dadosBancarios = tipoConta + "/" + agencia + "/" + conta;
            receita.setDadosBancarios(dadosBancarios);
        }
        return receitaRepository.findReceitaById(id);
    }

    @Override
    public List<TituloContabilReceita>buscarTodosTitulosContabeisReceitas(int id) {
        return receitaRepository.findByAllTitulosContabeisReceita(id);
    }


}
