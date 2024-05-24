package com.smartSpd.smartSpding.Core.DTO;

public class DashDTO {

    private Long identicador_balanco;
    private boolean dashboard_check;

    public DashDTO(Long identicador_balanco, boolean is_dashboard) {
        this.identicador_balanco = identicador_balanco;
        this.dashboard_check = is_dashboard;
    }

    public Long getIdenticador_balanco() {
        return identicador_balanco;
    }

    public void setIdenticador_balanco(Long identicador_balanco) {
        this.identicador_balanco = identicador_balanco;
    }

    public boolean isDashboard_check() {
        return dashboard_check;
    }

    public void setDashboard_check(boolean dashboard_check) {
        this.dashboard_check = dashboard_check;
    }
}
