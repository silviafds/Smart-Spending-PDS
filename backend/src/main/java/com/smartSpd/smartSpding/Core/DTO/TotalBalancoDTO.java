package com.smartSpd.smartSpding.Core.DTO;

public class TotalBalancoDTO {
    private Double totalDespesa;
    private Double totalReceita;

    public TotalBalancoDTO(Double totalDespesa, Double totalReceita) {
        this.totalDespesa = totalDespesa;
        this.totalReceita = totalReceita;
    }

    public Double getTotalDespesa() {
        return totalDespesa;
    }

    public void setTotalDespesa(Double totalDespesa) {
        this.totalDespesa = totalDespesa;
    }

    public Double getTotalReceita() {
        return totalReceita;
    }

    public void setTotalReceita(Double totalReceita) {
        this.totalReceita = totalReceita;
    }
}
