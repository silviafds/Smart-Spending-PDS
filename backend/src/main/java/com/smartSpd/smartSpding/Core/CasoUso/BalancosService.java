package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BalancosService {
    List<?> gerenciadorBalancos(BalancoRapidoDTO balancoRapidoDTO);
    void registrarBalanco(Balancos balancos);

    void editarBalanco(Balancos balancos);

    void editarBalancoNoDashboard(DashDTO dto);

    List<Balancos> buscarTodosBalancos();

    List<Balancos> buscarBalancosHospital();

    List<Balancos> buscarBalancosSupermercado();

    List<Balancos> buscarBalancosRestaurante();

    Balancos buscarBalancoPorId(Long id);

    void deletarBalanco(Long id);
}
