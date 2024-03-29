import React, {useState} from 'react';
import {MdHelpOutline} from "react-icons/md";
import {AjudaEnum} from "../../core/ENUM/Ajuda";

interface AjudaProps {
    tipoAjuda: AjudaEnum; // Tipo de ajuda a ser exibido
}

export function Ajuda({ tipoAjuda }: AjudaProps) {
    const [mostrarMensagem, setMostrarMensagem] = useState(false);

    const handleMouseEnter = () => {
        setMostrarMensagem(true);
    };

    const handleMouseLeave = () => {
        setMostrarMensagem(false);
    };

    let mensagemAjuda = '';

    switch (tipoAjuda) {
        case AjudaEnum.DEFINICAO_CONTA_INTERNA:
            mensagemAjuda = AjudaEnum.DEFINICAO_CONTA_INTERNA;
            break;
        case AjudaEnum.CADASTRO_CONTA_INTERNA:
            mensagemAjuda = AjudaEnum.CADASTRO_CONTA_INTERNA;
            break;
        case AjudaEnum.DEFINICAO_RECEITA:
            mensagemAjuda = AjudaEnum.DEFINICAO_RECEITA;
            break;
        case AjudaEnum.CADASTRO_RECEITA:
            mensagemAjuda = AjudaEnum.CADASTRO_RECEITA;
            break;
        case AjudaEnum.CADASTRO_CONTA_BANCARIA:
            mensagemAjuda = AjudaEnum.CADASTRO_CONTA_BANCARIA;
            break;
        case AjudaEnum.DEFINICAO_ANALISE:
            mensagemAjuda = AjudaEnum.DEFINICAO_ANALISE;
            break;
        case AjudaEnum.DEFINICAO_DESPESA:
            mensagemAjuda = AjudaEnum.DEFINICAO_DESPESA;
            break;
        case AjudaEnum.CADASTRO_DESPESA:
            mensagemAjuda = AjudaEnum.CADASTRO_DESPESA;
            break;
        default:
            mensagemAjuda = 'Mensagem padrão de ajuda.';
            break;
    }

    return (
        <div className="relative inline-block">
            <MdHelpOutline
                className="inline-block w-6 h-6 cursor-pointer"
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}
            />
            {mostrarMensagem && (
                <div className="absolute right-2 bg-gray-100 p-3 w-48 rounded-lg shadow-md mt-1">
                    {mensagemAjuda}
                </div>
            )}
        </div>
    );
}
