package com.smartSpd.smartSpding.Aplicacao.casoDeUso;

import com.smartSpd.smartSpding.Core.Dominio.Origem;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrigemService {

    List<Origem> buscarTodasOrigens();
}
