import React, {useEffect, useState} from "react";

function Patrimonio() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);


    return (
        <div>
            <h1>pagina de Patrimonio</h1>
        </div>
    );
}
export default Patrimonio;