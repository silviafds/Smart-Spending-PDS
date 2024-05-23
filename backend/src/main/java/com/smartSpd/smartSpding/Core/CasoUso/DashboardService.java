package com.smartSpd.smartSpding.Core.CasoUso;


import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {


    String salvarBalancoDashboard(Long id);

    List<List<?>> buscarBalancosDashboard();
    
    String deletarBalancoDashboard(Long id);

}