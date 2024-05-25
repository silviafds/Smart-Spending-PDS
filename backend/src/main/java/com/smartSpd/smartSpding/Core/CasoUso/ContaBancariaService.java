package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.Bancos;
import com.smartSpd.smartSpding.Core.Classes.DadosBancarios;
import com.smartSpd.smartSpding.Core.DTO.ContaBancariaDTO;
import com.smartSpd.smartSpding.Core.Dominio.ContaBancaria;

import java.util.List;

public interface ContaBancariaService {

    Boolean cadastrarContaBancaria(ContaBancariaDTO data);

    Boolean editarContaBancaria(ContaBancariaDTO data);

    List<ContaBancaria> buscarContaBacaria();

    Boolean deletarContaBacaria(int id);

    List<ContaBancaria> buscarContaBancariaPorId(int id);

    List<Bancos> buscarBancoPeloNome();

    List<DadosBancarios> buscarDadosBancariosPorBanco(String banco);

}