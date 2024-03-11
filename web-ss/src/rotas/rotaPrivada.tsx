import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from '../contexto/Auth';

export const RotaPrivada = () => {
    const { user } = useAuth();
    const signed = !!user;
    return signed ? <Outlet /> : <Navigate to="/" />;
};
