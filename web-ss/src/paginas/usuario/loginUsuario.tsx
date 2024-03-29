import React, {useState} from 'react';
import {HeaderLogin} from "../../componentes/header/headerLogin";
import {useForm} from "react-hook-form";
import '../../styles/form/formCadastro.css';
import '../../styles/form/fontes.css'
import {verificarSenhaLogin} from "../../logica/cadastroUsuario/verificaSenhaCadastro";
import {FaRegEye, FaRegEyeSlash} from "react-icons/fa";
import axios from 'axios';
import '../../core/DTO/UserDTO';
import { AuthenticationDTO } from "../../core/DTO/AuthenticationDTO";
import { BACKEND_URL } from "../../core/config/index";
import Swal from 'sweetalert2'
import { useNavigate } from 'react-router-dom';  // Importando useNavigate

interface IFormInputs {
    login: string;
    password: string;
}

function formataParaJson(dadoUsuario: AuthenticationDTO) {
    let dados = {
        login: dadoUsuario.login,
        password: dadoUsuario.password
    };
    return JSON.stringify(dados);
}

interface LoginUsuarioProps {
    setNomeUsuario: React.Dispatch<React.SetStateAction<string>>;
    onLogin?: () => void; // Adicione esta linha
}

function LoginUsuario({ setNomeUsuario, onLogin }: LoginUsuarioProps) {
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<IFormInputs>();

    const [mostrarSenha, setMostrarSenha] = useState(false);
    //const { signIn, signed } = useContext(AuthContext);

    const onSubmit = async (data: IFormInputs) => {
        const mensagemSenhaVazia = verificarSenhaLogin(data.password);

        if(mensagemSenhaVazia) {
            const dadoUsuario = new AuthenticationDTO(data.login, data.password);
            const JsonData = formataParaJson(dadoUsuario);
            const axiosConfig = {headers: { 'Content-Type': 'application/json'}};

            const dado = {
                JsonData,
                axiosConfig,
            };
            //await signIn(dado);

            await axios.post(BACKEND_URL + "/auth/login", JsonData, axiosConfig)
                .then((response) => {
                    // Captura o token da resposta
                    const token = response.data.token;
                    // Armazena o token localmente (pode usar localStorage, sessionStorage, ou outro método seguro)
                    localStorage.setItem('token', token);
                    // Configura o cabeçalho "Authorization" para enviar o token nas próximas solicitações
                    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                    console.log("recebendo token do backend: "+token)
                    if (onLogin) {
                        onLogin(); // Chame onLogin se estiver definido
                    }

                    setNomeUsuario(dadoUsuario.login)
                    navigate('/SmartSpending');
                })
                .catch(function (error) {
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
                });
        }
    };
   // console.log(signed);

    const alternarVisibilidadeSenha = () => {
        setMostrarSenha(!mostrarSenha);
    };

    return (
        <div className="cadastro-usuario">
            <HeaderLogin />
            <div className="flex flex-col justify-center items-center border border-amber-300 custom-height sm:w-4/5 md:w-3/5 lg:w-5/12
            mx-auto rounded-2xl p-4">
                <h1 className="text-center mb-4 text-xl">Login</h1>

                <form onSubmit={handleSubmit(onSubmit)}
                      className="flex-col text-start w-full sm:w-3/4 md:w-4/5 lg:w-3/4">

                    <div className="inputs relative my-4">
                        <input {...register("login", {required: true})} placeholder="Login"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.login && (<p>Insira o seu username.</p>)}

                    <div className="inputs relative my-4">
                        <input {...register("password", {required: true})} placeholder="Senha"
                               type={mostrarSenha ? 'text' : 'password'}
                               className="input-with-line w-full"/>
                        <div className="absolute bottom-3 right-4 text-orange-400" onClick={alternarVisibilidadeSenha}>
                            {mostrarSenha ? <FaRegEye/> : <FaRegEyeSlash/>}
                        </div>
                        <div className="line"></div>
                    </div>
                    {errors.password && (<p>Insira a senha.</p>)}

                    <div className="w-full p-2">
                        <input type="submit"
                               className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"/>
                    </div>

                </form>
            </div>
        </div>
    );
}

export default LoginUsuario;