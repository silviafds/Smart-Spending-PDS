package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancoService;
import com.smartSpd.smartSpding.Core.Classes.Balanco;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalancoServiceImpl implements BalancoService {

    private final BalancoRepository balancoRepository;

    public BalancoServiceImpl(BalancoRepository balancoRepository) {
        this.balancoRepository = balancoRepository;
    }

    @Override
    @Transactional
    public void salvarBalanco(Balanco balanco) {
        if (balanco == null) {
            throw new IllegalArgumentException("O balanço não pode ser nulo");
        }
        balancoRepository.save(balanco);
    }

    @Override
    public Balanco buscarBalancoPorId(Long id) {
        return balancoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Balanco> buscarTodosBalancos() {
        List<Balanco> balancos = balancoRepository.findAll();

        if (balancos.isEmpty()) {
            throw new RuntimeException("Não há nenhum balanço.");
        }

        return balancos;
    }

}
