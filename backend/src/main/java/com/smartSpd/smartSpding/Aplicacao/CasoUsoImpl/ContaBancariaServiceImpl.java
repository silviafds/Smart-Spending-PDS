package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ContaBancariaService;
import com.smartSpd.smartSpding.Core.Classes.Bancos;
import com.smartSpd.smartSpding.Core.Classes.DadosBancarios;
import com.smartSpd.smartSpding.Core.DTO.ContaBancariaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaBancaria;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ContaBancariaRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ContaBancariaServiceImpl implements ContaBancariaService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaServiceImpl(ContaBancariaRepository contaBancariaRepository) {
        this.contaBancariaRepository = contaBancariaRepository;
    }

    @Transactional
    @Override
    public Boolean cadastrarContaBancaria(ContaBancariaDTO data) {
        if(data.id() == null) {
            try {
                ContaBancaria contaBancaria = new ContaBancaria();
                contaBancaria.setId(data.id());
                contaBancaria.setNomeBanco(data.nomeBanco());
                contaBancaria.setTipoConta(data.tipoConta());
                contaBancaria.setAgencia(data.agencia());
                contaBancaria.setNumeroConta(data.numeroConta());
                contaBancariaRepository.save(contaBancaria);
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Erro ao cadastrar nova conta bancária. ", e);
            }
        }
        return false;
    }

    @Override
    public Boolean editarContaBancaria(ContaBancariaDTO data) {
        try {
            contaBancariaRepository.editarContaBancaria(
                    data.id(),
                    data.nomeBanco(),
                    data.tipoConta(),
                    data.agencia(),
                    data.numeroConta()
            );
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar conta bancária. ", e);
        }
        return false;
    }

    public List<ContaBancaria> buscarContaBacaria() {
        return contaBancariaRepository.findAll();
    }

    @Transactional
    @Override
    public Boolean deletarContaBacaria(int id) {
        try {
            return contaBancariaRepository.deleteContaBancaria(id) == 1;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar conta bancária. ", e);
        }
        return false;
    }

    @Override
    public List<ContaBancaria> buscarContaBancariaPorId(int id) {
        return contaBancariaRepository.findContaBancariaById(id);
    }

    @Override
    public List<Bancos> buscarBancoPeloNome() {
        List<String> contas = new ArrayList<>();
        contas = contaBancariaRepository.findContaBancariaByNomeBanco();

        List<Bancos> listaContasBancarias = new ArrayList<>();
        for (int i = 0; i < contas.size(); i++) {
            listaContasBancarias.add(new Bancos(i + 1, contas.get(i)));
        }

        return listaContasBancarias;
    }

    @Override
    public List<DadosBancarios> buscarDadosBancariosPorBanco(String banco) {
        List<String> contas = new ArrayList<>();
        contas = contaBancariaRepository.findContaBancariaByNomeBanco2(banco);

        List<DadosBancarios> listaContasBancarias = new ArrayList<>();
        for (int i = 0; i < contas.size(); i++) {
            String conta = contas.get(i);
            String[] partes = conta.split(",");
            String contaFormatada = String.join("/", partes);

            listaContasBancarias.add(new DadosBancarios(i + 1, contaFormatada));
        }

        return listaContasBancarias;
    }

}
