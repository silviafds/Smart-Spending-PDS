import { createContext, ReactNode, useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { BACKEND_URL } from '../config';
import Swal from 'sweetalert2';

interface User {
    login: string;
    token: string;
}

interface AuthContextType {
    user: User | null;
    signIn: (data: { login: string; password: string }) => Promise<void>;
    signOut: () => void;
    signed: boolean;
}

export const AuthContext = createContext<AuthContextType>({
    user: null,
    signIn: async () => {},
    signOut: () => {},
    signed: false,
});

export const useAuth = () => useContext(AuthContext);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [user, setUser] = useState<User | null>(() => {
        const storageUser = localStorage.getItem('@Auth:user');
        return storageUser ? JSON.parse(storageUser) : null;
    });

    useEffect(() => {
        localStorage.setItem('@Auth:user', JSON.stringify(user));
    }, [user]);

    const signIn = async (data: { login: string; password: string }) => {
        try {
            const response = await axios.post(BACKEND_URL + '/auth/login', data);
            const userData = response.data;
            setUser(userData);
            axios.defaults.headers.common['Authorization'] = `Bearer ${userData.token}`;
            localStorage.setItem('@Auth:token', userData.token);
            localStorage.setItem('@Auth:user', JSON.stringify(userData));
            localStorage.setItem('nomeUser', data.login);
        } catch (error: any) {
            if (error.response) {
                console.log(error.response.data);
                console.log(error.response.status);
                console.log(error.response.headers);
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: error.response.data,
                    customClass: {
                        confirmButton: 'error-button'
                    }
                });
            } else if (error.request) {
                console.log(error.request);
            } else {
                console.log('Error', error.message);
            }
            console.log(error.config);
        }
    };

    const signOut = () => {
        setUser(null);
        localStorage.removeItem('@Auth:token');
        localStorage.removeItem('@Auth:user');
        localStorage.removeItem('nomeUser');
        localStorage.clear();
        window.location.href = '/'; // Redirecionar para a página inicial
    };

    return (
        <AuthContext.Provider value={{ user, signIn, signOut, signed: !!user }}>
            {children}
        </AuthContext.Provider>
    );
};


