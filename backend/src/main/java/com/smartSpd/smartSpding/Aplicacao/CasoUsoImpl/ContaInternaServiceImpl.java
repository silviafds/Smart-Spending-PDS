package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ContaInternaService;
import com.smartSpd.smartSpding.Core.DTO.ContaInternaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import com.smartSpd.smartSpding.Core.Dominio.Despesa;
import com.smartSpd.smartSpding.Core.Dominio.Receita;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ContaInternaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.DespesaRepository;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class ContaInternaServiceImpl implements ContaInternaService {

    private final ContaInternaRepository contaInternaRepository;
    private ReceitaRepository receitaRepository;
    private DespesaRepository despesaRepository;

    @Autowired
    private final ContaInterna contaInterna;

    public ContaInternaServiceImpl(ContaInternaRepository contaInternaRepository, ContaInterna contaInterna) {
        this.contaInternaRepository = contaInternaRepository;
        this.contaInterna = contaInterna;
    }

    @Transactional
    public Boolean cadastrarContaInterna(ContaInternaDTO data) {
        ContaInterna contaExistente = contaInternaRepository.findByNome(data.nome());

        if (Objects.isNull(contaExistente)) {
            ContaInterna novaConta = new ContaInterna();
            novaConta.setNome(data.nome());
            contaInternaRepository.save(novaConta);
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean editarContaInterna(ContaInternaDTO data) {
        return contaInternaRepository.atualizarNomeContaInterna(data.id(), data.nome()) == 1;
    }

    @Transactional
    public Boolean editarStatusContaInterna(ContaInternaDTO data) {
        return contaInternaRepository.atualizarStatusContaInterna(data.id(), data.desabilitarConta()) == 1;
    }

    @Override
    public Boolean deletarContaInterna(int data) { return contaInternaRepository.deletarContaInterna(data) == 1; }

    @Override
    public List<ContaInterna> buscarTodasContasInterna() {
        return contaInternaRepository.findByAllContaInterna();
    }

    @Override
    public List<ContaInterna> buscarTodasContasInternaHabilitadas() {
        return contaInternaRepository.findByAllContaInternaHabilitadas();
    }

    @Override
    public ContaInterna buscarContaInternaPeloId(int data) {
        return contaInternaRepository.findByIdContaInterna(data);
    }

    @Override
    public Double calcularSaldoTotalPorConta(ContaInterna conta) {
        Double saldoTotal = 0.0;

        List<Receita> receitas = receitaRepository.findByContaInterna(conta);
        for (Receita receita : receitas) {
            saldoTotal += receita.getValorReceita();
        }

        List<Despesa> despesas = despesaRepository.findByContaInterna(conta);
        for (Despesa despesa : despesas) {
            saldoTotal -= despesa.getValorDespesa();
        }
        return saldoTotal;
    }
}
