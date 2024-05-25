package com.smartSpd.smartSpding.Core.CasoUso;

import java.util.List;

import com.smartSpd.smartSpding.Core.DTO.DashDTO;
import com.smartSpd.smartSpding.Core.Dominio.Dash;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

    List<Dash> buscarBalancosNoDashboard();

    String salvarBalancoDashboard(DashDTO dto);

    List<List<?>> buscarBalancosDashboard();
    
    String deletarBalancoDashboard(DashDTO dto);

}