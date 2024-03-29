import React from 'react';
import {Link} from "react-router-dom";
import { IoPersonOutline } from "react-icons/io5";

export const HeaderCadastro = () => {

    return (
        <header className={"flex justify-between items-center px-6 py-4 mb-5 bg-principal"}>
            <h2 className={"text-white font-bold text-2xl"}>Smart Spending</h2>
            <nav className={"flex gap-6 items-center text-white"}>
                <Link to="http://localhost:3000/"><IoPersonOutline size={20} /></Link>
                <Link to="/quemsomos">Quem Somos</Link>
                <Link to="/contato">Contato</Link>
            </nav>
        </header>
    );
};
