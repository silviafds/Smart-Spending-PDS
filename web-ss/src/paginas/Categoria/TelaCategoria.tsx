import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../componentes/header/headerPadrao";
import {Sidebar} from "../../componentes/sidebar/sidebar";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";


function TelaCategoria() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    const handleCadastro = async () => {
        window.location.href = "/cadastroCategoria/";
    };


    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Gerenciador de Categorias </h1>
                        <Ajuda tipoAjuda={AjudaEnum.GERENCIADOR_CATEGORIA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-24 rounded-md"
                            onClick={handleCadastro}>
                        Cadastrar
                    </button>

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md ml-4"
                            onClick={handleCadastro}>
                        Ver Associação
                    </button>

                    <div className="mt-10 w-full max-w-6xl ">

                        <div className="flex justify-center items-center">

                            <h1>ksjjkdhs</h1>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TelaCategoria;