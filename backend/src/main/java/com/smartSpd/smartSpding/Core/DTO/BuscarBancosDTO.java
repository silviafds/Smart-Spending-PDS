package com.smartSpd.smartSpding.Core.DTO;

public class BuscarBancosDTO {
    private Long id;
    private String nomeBanco;

    public BuscarBancosDTO(Long id, String nomeBanco) {
        this.id = id;
        this.nomeBanco = nomeBanco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }
}