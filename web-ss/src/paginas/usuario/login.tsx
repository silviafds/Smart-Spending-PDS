import { HeaderLogin } from '../../componentes/header/headerLogin';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useAuth } from '../../core/contexto/Auth';
import { Navigate } from 'react-router-dom';

interface IFormInputs {
    login: string;
    password: string;
}

export const Login: React.FC = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const { signIn, signed } = useAuth();

    const {
        register,
        formState: { errors },
    } = useForm<IFormInputs>();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await signIn({ login, password });
    };

    if (signed) {
        return <Navigate to="/dashboard" />;
    }

    return (
        <div className="cadastro-usuario">
            <HeaderLogin />
            <div className="flex flex-col justify-center items-center border border-sky-950 custom-height sm:w-4/5 md:w-3/5 lg:w-5/12 mx-auto rounded-2xl p-4">
                <h1 className="text-center mb-4 text-xl">Login</h1>
                <form onSubmit={handleSubmit} className="flex-col text-start w-full sm:w-3/4 md:w-4/5 lg:w-3/4">
                    <div className="inputs relative my-4">
                        <input
                            {...register('login', { required: true })}
                            placeholder="Login"
                            className="input-with-line w-full"
                            onChange={(e) => setLogin(e.target.value)}
                        />
                        <div className="line"></div>
                    </div>
                    {errors.login && <p>Insira o seu username.</p>}
                    <div className="inputs relative my-4">
                        <input
                            {...register('password', { required: true })}
                            placeholder="Senha"
                            type="password"
                            className="input-with-line w-full"
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        <div className="line"></div>
                    </div>
                    {errors.password && <p>Insira a senha.</p>}
                    <div className="w-full p-2">
                        <input
                            type="submit"
                            className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"
                            value="Login"
                        />
                    </div>
                </form>
            </div>
        </div>
    );
};