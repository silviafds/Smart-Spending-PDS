package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ContaInternaService;
import com.smartSpd.smartSpding.Core.DTO.ContaInternaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaInterna;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ContaInternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class ContaInternaServiceImpl implements ContaInternaService {

    private final ContaInternaRepository contaInternaRepository;

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
            novaConta.setDesabilitarConta(data.desabilitarConta());
            contaInternaRepository.save(novaConta);
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean editarContaInterna(ContaInternaDTO data) {
        return contaInternaRepository.atualizarNomeContaInterna(data.id(), data.nome(), data.desabilitarConta()) == 1;
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
}
