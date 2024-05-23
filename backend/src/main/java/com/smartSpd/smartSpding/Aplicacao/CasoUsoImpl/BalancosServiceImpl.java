package com.smartSpd.smartSpding.Aplicacao.CasoUsoImpl;

import com.smartSpd.smartSpding.Core.CasoUso.BalancosService;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;
import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import com.smartSpd.smartSpding.Core.Excecao.BalancoNaoEncontradoException;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.BalancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.javapoet.ClassName;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.ORIGENS_MAIS_RENTAVEIS;
import static com.smartSpd.smartSpding.Core.Enum.TiposBalanco.PAGAMENTOS_MAIS_UTILIZADOS;

@Component
public class BalancosServiceImpl implements BalancosService {

    private final BalancosRepository balancosRepository;

    static Logger log = Logger.getLogger(String.valueOf(ClassName.class));

    @Autowired
    public BalancosServiceImpl(BalancosRepository balancosRepository) {
        this.balancosRepository = balancosRepository;
    }

    @Override
    public void registrarBalanco(Balancos balancos) {
        try {
            if (balancos == null) {
                throw new IllegalArgumentException("O balanço não pode ser nulo.");
            }

            if (balancos.getNome() == null || balancos.getNome().isEmpty()) {
                throw new IllegalArgumentException("É preciso fornecer um nome para o balanço.");
            }

            if (balancos.getTipoBalanco() == null || balancos.getTipoBalanco().isEmpty()) {
                throw new IllegalArgumentException("É preciso fornecer o tipo do balanço.");
            }

            if (balancos.getAnalise_balanco() == null || balancos.getAnalise_balanco().isEmpty()) {
                throw new IllegalArgumentException("É preciso fornecer uma análise de balanço.");
            }

            if (balancos.getData_inicio() == null || balancos.getData_termino() == null) {
                throw new IllegalArgumentException("As datas de início e término de balanço não podem ser nulas.");
            }

            if (!Objects.equals(balancos.getAnalise_balanco(), PAGAMENTOS_MAIS_UTILIZADOS.getTiposBalanco()) &&
                    !Objects.equals(balancos.getAnalise_balanco(), ORIGENS_MAIS_RENTAVEIS.getTiposBalanco())) {
                if (balancos.getCategoria_titulo_contabil() == null || balancos.getCategoria_titulo_contabil().isEmpty()) {
                    throw new IllegalArgumentException("É preciso fornecer uma categoria ou título contábil.");
                }
            }

            balancosRepository.save(balancos);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "Erro ao registrar balanço: ", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao tentar registrar balanço", e);
            throw new RuntimeException("Ocorreu um erro ao registrar o balanço.", e);
        }
    }


    @Override
    public void editarBalanco(Balancos dados) {
        try {
            if (Objects.equals(dados.getAnalise_balanco(), PAGAMENTOS_MAIS_UTILIZADOS.getTiposBalanco()) ||
                    Objects.equals(dados.getAnalise_balanco(), ORIGENS_MAIS_RENTAVEIS.getTiposBalanco())) {
                if (dados.getNome() == null || dados.getNome().isEmpty() ||
                        dados.getData_inicio() == null || dados.getData_termino() == null ||
                        dados.getTipo_visualizacao() == null || dados.getTipo_visualizacao().isEmpty()) {
                    throw new IllegalArgumentException("É preciso preencher todos os campos para editar o balanço!.");
                }
            } else {
                if (dados.getNome() == null || dados.getNome().isEmpty() ||
                dados.getTipoBalanco() == null || dados.getTipoBalanco().isEmpty() ||
                dados.getAnalise_balanco() == null || dados.getAnalise_balanco().isEmpty() ||
                        dados.getData_inicio() == null || dados.getData_termino() == null ||
                        dados.getTipo_visualizacao() == null || dados.getTipo_visualizacao().isEmpty() ||
                dados.getCategoria_titulo_contabil() == null || dados.getCategoria_titulo_contabil().isEmpty()) {
                    throw new IllegalArgumentException("É preciso preencher todos os campos para editar o balanço!");
                }
            }

            Optional<Balancos> balancoOptional = balancosRepository.findById(dados.getId());
            if (!balancoOptional.isPresent()) {
                throw new NoSuchElementException("Balanço não encontrado para esse id: " + dados.getId());
            }

            Balancos balancoExistente = balancoOptional.get();
            balancoExistente.setNome(dados.getNome());
            balancoExistente.setTipoBalanco(dados.getTipoBalanco());
            balancoExistente.setAnalise_balanco(dados.getAnalise_balanco());
            balancoExistente.setData_inicio(dados.getData_inicio());
            balancoExistente.setData_termino(dados.getData_termino());
            balancoExistente.setTipo_visualizacao(dados.getTipo_visualizacao());
            balancoExistente.setCategoria_titulo_contabil(dados.getCategoria_titulo_contabil());

            balancosRepository.save(balancoExistente);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "Erro ao editar o balanço: ", e);
            throw e;
        } catch (NoSuchElementException e) {
            log.log(Level.SEVERE, "Balanço não encontrado para o id que você forneceu ", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao tentar editar o balanço: ", e);
            throw e;
        }
    }

    @Override
    public List<Balancos> buscarTodosBalancos() {
        try {
            List<Balancos> balanco = balancosRepository.buscarTodosBalancos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            balanco.forEach(balancos -> {
                LocalDate dataInicio = balancos.getData_inicio();
                String dataFormatada = dataInicio.format(formatter);
                balancos.setData_inicio_balanco(dataFormatada);

                LocalDate dataFim = balancos.getData_termino();
                String dataFormatadaFinal = dataFim.format(formatter);
                balancos.setData_final_balanco(dataFormatadaFinal);
            });

            return balanco;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar todos os balanços no service. ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Balancos buscarBalancoPorId(Long id) {
        try {
            Optional<Balancos> balancoOptional = balancosRepository.findById(id);
            return balancoOptional.orElse(null);
        } catch (IllegalArgumentException e) {
            log.log(Level.SEVERE, "Id inválido", e);
            throw new IllegalArgumentException("Id fornecido é inválido.", e);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao buscar balanço por id ", e);
            throw new RuntimeException("Erro ao buscar balanço por id: ", e);
        }
    }

    @Override
    public void deletarBalanco(Long id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("O id é inválido.");
            }

            Optional<Balancos> balancoOptional = balancosRepository.findById(id);

            if (!balancoOptional.isPresent()) {
                throw new BalancoNaoEncontradoException("Balanço não encontrado para o id: " + id);
            }

            balancosRepository.delete(balancoOptional.get());
        } catch (IllegalArgumentException e) {
            log.warning("Id do balanço é inválido.");
            throw new IllegalArgumentException("O id do balanço é inválido. ", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Erro ao deletar balanço no serviço. ", e);
            throw new RuntimeException("Erro ao deletar balanço.", e);
        }
    }

//	@Override
//	public void setDashboard(Dashboard dashboard) {
//		balancosRepository.buscarTodosBalancos().add(dashboard);
//		
//	}
}
