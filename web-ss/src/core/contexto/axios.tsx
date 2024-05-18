import axios from 'axios';
import { BACKEND_URL } from "../config";
import {Navigate} from "react-router-dom";

const instance = axios.create({
    baseURL: BACKEND_URL,
});

const signOut = () => {
    localStorage.removeItem('@Auth:token');
    localStorage.removeItem('@Auth:user');
    localStorage.removeItem('nomeUser');
    localStorage.clear();
    return <Navigate to="/" />;
};

instance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('@Auth:token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

instance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        console.log("sttatus do error: "+error.response.status)
        if (error.response && error.response.status === 401) {
            // Token expirado ou inválido, deslogar o usuário
            signOut(); // Deslogar o usuário e redirecionar para a página de login
            window.location.href = '/'; // Redirecionar para a página de login
        }
        return Promise.reject(error);
    }
);

export default instance;