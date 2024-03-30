import React, {useEffect, useState} from "react";

function Projetos() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);


    return (
        <div>
            <h1>pagina de Projetos</h1>
        </div>
    );
}
export default Projetos;