package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.ProjetosService;
import com.smartSpd.smartSpding.Core.Dominio.Projetos;
import com.smartSpd.smartSpding.Core.Excecao.ProjetoInvalidoException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.ProjetosRepository;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class ProjetosServiceImpl implements ProjetosService {
    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    private final ProjetosRepository projetosRepository;

    public ProjetosServiceImpl(ProjetosRepository projetosRepository) {
        this.projetosRepository = projetosRepository;
    }

    @Override
    public void salvarProjetos(Projetos data) {
        try {
            if ((data.getNome().isEmpty() || data.getCategoria().isEmpty()) ||
                data.getValor_meta().isEmpty() || data.getData_final()==null ||
                data.getData_inicio()==null || data.getDescricao().isEmpty()) {
                throw new ProjetoInvalidoException("Necessário preencher campos para salvar o projeto.");
            }

            projetosRepository.save(data);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao cadastrar um novo projeto. ", e);
            throw e;
        }
    }

    @Override
    public List<Projetos> buscarTodosProjetos() {
        try {
            List<Projetos> projetos = projetosRepository.buscarTodosProjetos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            projetos.forEach(projeto -> {
                LocalDate dataProjetoInicio = projeto.getData_inicio();
                String dataFormatadaInicio = dataProjetoInicio.format(formatter);
                projeto.setDataFormatadaInicio(dataFormatadaInicio);

                LocalDate dataProjetoFinal = projeto.getData_final();
                String dataFormatadaFinal = dataProjetoFinal.format(formatter);
                projeto.setDataFormatadaFinal(dataFormatadaFinal);
            });

            return projetos;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os projetos no service.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void editarProjeto(Projetos data) {
        try {
            if ((data.getNome().isEmpty() || data.getCategoria().isEmpty()) ||
                    data.getValor_meta().isEmpty() || data.getData_final()==null ||
                    data.getData_inicio()==null || data.getDescricao().isEmpty()) {
                throw new ProjetoInvalidoException("Necessário preencher campos para editar o projeto.");
            }

            projetosRepository.save(data);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao editar um projeto. ", e);
            throw e;
        }
    }

    @Override
    public void deletarProjeto(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("ID do projeto está nulo.");
            }

            Optional<Projetos> projetosOptional = projetosRepository.findById(id);

            if(!projetosOptional.isPresent()) {
                throw new ProjetoInvalidoException("Projeto com ID "+ id + " não encontrado");
            }

            projetosRepository.delete(projetosOptional.get());
        } catch (IllegalArgumentException e) {
            log.warning("Id de projeto está nulo.");
            throw new IllegalArgumentException("Id de projeto inválido.", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar projeto no service.", e);
            throw new RuntimeException("Erro ao deletar projeto.", e);
        }
    }

    @Override
    public List<Projetos> buscarProjetoPorId(Long id) {
        return projetosRepository.buscarProjetoPorID(id);
    }
}
