import axios from 'axios';
import { BACKEND_URL } from "../core/config";

const instance = axios.create({
    baseURL: BACKEND_URL,
});

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

export default instance;