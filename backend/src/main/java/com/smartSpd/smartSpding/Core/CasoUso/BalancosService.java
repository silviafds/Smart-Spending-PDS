package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.BalancoDespesaReceita;
import com.smartSpd.smartSpding.Core.DTO.BalancoRapidoDTO;
import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;

import com.smartSpd.smartSpding.Core.Enum.TiposBalanco;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BalancosService {

    void registrarBalanco(Balancos balancos);

    void editarBalanco(Balancos balancos);

    void editarBalancoNoDashboard(DashDTO dto);

    List<Balancos> buscarTodosBalancos();

    List<Balancos> buscarBalancosHospital();

    List<Balancos> buscarBalancosSupermercado();

    List<Balancos> buscarBalancosRestaurante();

    Balancos buscarBalancoPorId(Long id);

    void deletarBalanco(Long id);

    BalancoStrategy verificaStrategy(TiposBalanco tiposBalanco);

    TiposBalanco getTiposBalancoFromString(String analiseBalanco);

    List<BalancoDespesaReceita> gerarBalancoDespesa(BalancoRapidoDTO balancoRapidoDTO);

    List<BalancoDespesaReceita> gerarBalancoInvestimento(BalancoRapidoDTO balancoRapidoDTO);


}
