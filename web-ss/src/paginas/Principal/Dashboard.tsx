import React, {useEffect, useState} from "react";

function Dashboard() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);


    return (
        <div>
            <h1>pagina de Dashboard</h1>
        </div>
    );
}
export default Dashboard;