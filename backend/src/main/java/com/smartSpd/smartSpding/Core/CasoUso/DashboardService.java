package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Dominio.Dashboard;
import com.smartSpd.smartSpding.Core.Dominio.Balancos;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

    public void createDashboard(String nome, Set<Balancos> balancos);
    
    Dashboard buscarDashboard();
    
    public void addBalanco(Balancos balanco);
    
    public void removeBalanco(Balancos balanco);
    
    public void deletarDashboard();
}