import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import BasicModal from "../../paginas/Balanco/Modal/BasicModal";

function LancamentoBalanco() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [modalAberto, setModalAberto] = useState<boolean>(false);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    function handleCadastro() {
        // Lógica para lidar com o cadastro
    }

    function handleAbrirModal() {
        setModalAberto(true);
    }

    function handleFecharModal() {
        setModalAberto(false);
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />

            <div className={"flex"}>
                <Sidebar />
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Balanços de Lançamentos </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_BALANCO_LANCAMENTO} />
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"} />

                    <div className="mt-10 w-full max-w-6xl ">

                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md"
                                onClick={handleCadastro}>
                            Criar Balanço
                        </button>

                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md ml-4"
                                onClick={handleAbrirModal}>
                            Balanço Rápido
                        </button>

                        {modalAberto && <BasicModal onClose={handleFecharModal} />}

                    </div>
                </div>
            </div>
        </div>
    );
}

export default LancamentoBalanco;
