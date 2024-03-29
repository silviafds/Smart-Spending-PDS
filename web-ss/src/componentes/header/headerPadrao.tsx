import React from "react";
import {Dropdown} from "../dropdown/dropdown";
import 'tailwindcss/tailwind.css';

interface HeaderPadraoProps {
    nomeUsuario: string;
}

export const HeaderPadrao = ({ nomeUsuario }: HeaderPadraoProps) => {
    return (
        <>
            <header className={"flex justify-between w-full items-center px-4 py-2 bg-principal"}>
                <h2 className={"text-white font-bold text-2xl flex-grow"}>HOME</h2>
                <nav className={"flex gap-4 items-center text-white px-3 py-1 rounded-lg cursor-pointer"}>
                    <div className="flex items-center font-bold">
                        <Dropdown nomeUsuario={nomeUsuario} />
                    </div>
                </nav>
            </header>
        </>
    );
};

