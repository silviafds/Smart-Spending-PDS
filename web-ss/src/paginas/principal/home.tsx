import React, { useEffect, useState } from 'react';
import { HeaderPadrao } from "../../components/header/headerPadrao";
import { Sidebar } from "../../components/sidebar/sidebar";

function Home() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    return (
        <div className="cadastro-usuario">
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <Sidebar />

        </div>
    );
}

export default Home;
