package com.smartSpd.smartSpding.Core.CasoUso;

import com.smartSpd.smartSpding.Core.Classes.MeioOrigem;
import com.smartSpd.smartSpding.Core.DTO.MeioOrigemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeioOrigemService {

    List<MeioOrigem> listagemMeiosOrigem();

    List<MeioOrigem> balancoMeioOrigem(MeioOrigemDTO meioOrigemDTO);
}