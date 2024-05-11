import React, {useState} from 'react';
import {HeaderCadastro} from "../../componentes/header/headerCadastro";
import {useForm} from "react-hook-form";
import '../../styles/form/formCadastro.css';
import '../../styles/form/fontes.css'
import {verificarSenhas} from "../../logica/cadastroUsuario/verificaSenhaCadastro";
import {FaRegEye, FaRegEyeSlash} from "react-icons/fa";
import axios from 'axios';
import '../../core/DTO/UserDTO';
import { UserDTO } from "../../core/DTO/UserDTO";
import { Role } from "../../core/ENUM/Role";
import { BACKEND_URL } from "../../core/config/index";
import Swal from "sweetalert2";

interface IFormInputs {
    nome: string;
    sobrenome: string;
    login: string;
    datanascimento: Date;
    email: string;
    password: string;
    verificaSenha: string;
}

function formataParaJson(dadoUsuario: UserDTO) {
    let dados = {
        nome: dadoUsuario.nome,
        sobrenome: dadoUsuario.sobrenome,
        login: dadoUsuario.login,
        datanascimento: dadoUsuario.datanascimento,
        email: dadoUsuario.email,
        password: dadoUsuario.password,
        role: dadoUsuario.role
    };
    return JSON.stringify(dados);
}

function CadastroUsuario() {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<IFormInputs>();

    const [mensagemErro, setMensagemErro] = useState('');
    const [mostrarSenha, setMostrarSenha] = useState(false); // Estado para controlar a visibilidade da senha

    const onSubmit = async (data: IFormInputs) => {
        const { password, verificaSenha } = data;
        const mensagemErro = verificarSenhas(password, verificaSenha);
        if (mensagemErro) {
            setMensagemErro(mensagemErro);
        } else {
            const dadoUsuario = new UserDTO(data.nome, data.sobrenome, data.login, data.datanascimento,
                data.email, data.password, Role.ADMIN);
            const JsonData = formataParaJson(dadoUsuario);
            const axiosConfig = {headers: { 'Content-Type': 'application/json'}};
            await axios.post(BACKEND_URL + "/auth/register", JsonData, axiosConfig)
                .then((response) => {
                    Swal.fire({
                        icon: "success",
                        title: "Cadastro criado.",
                        showConfirmButton: false,
                        timer: 1500
                    });
                })
                .catch(function (error) {
                    if (error.response) {
                        if (error.response && error.response.status === 400) {
                            const responseData = error.response.data;
                            console.error('Erro de BadRequest:', responseData);
                            Swal.fire({
                                icon: "error",
                                title: "Oops...",
                                text: responseData.message,
                                customClass: {
                                    confirmButton: 'error-button'
                                }
                            });
                        }
                    } else if (error.request) {
                        console.error("request: " + error.request);
                    } else {
                        console.error('Error', error.message);
                    }
                    console.error(error.config);
                });
        }
    };

    const alternarVisibilidadeSenha = () => {
        setMostrarSenha(!mostrarSenha);
    };

    return (
        <div className="cadastro-usuario">
            <HeaderCadastro />
            <div className="flex flex-col justify-center items-center border border-sky-950 sm:w-4/5 md:w-3/5 lg:w-5/12
            mx-auto rounded-2xl p-4">
                <h1 className="text-center mb-4 text-xl">Novo Cadastro</h1>

                <form onSubmit={handleSubmit(onSubmit)}
                      className="flex-col text-start w-full sm:w-3/4 md:w-4/5 lg:w-3/4">
                    <div className="inputs relative my-4">
                        <input {...register("nome", {required: true})} placeholder="Nome"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.nome && (<p>Insira seu nome.</p>)}

                    <div className="inputs relative my-4">
                        <input {...register("sobrenome", {required: true})} placeholder="Sobrenome"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.sobrenome && (<p>Insira seu sobrenome.</p>)}

                    <div className="inputs relative my-4">
                        <input {...register("login", {required: true})} placeholder="Login"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.login && (<p>Insira o seu username.</p>)}

                    <div className="input-data-aniversario inputs relative my-4">
                        <input {...register("datanascimento", {required: true})}
                               onFocus={(e) => (e.target.type = "date")}
                               onBlur={(e) => (e.target.type = "text")}
                               placeholder="Data de Nascimento"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.datanascimento && (<p>Insira a sua data de nascimento.</p>)}

                    <div className="inputs relative my-4">
                        <input {...register("email", {required: true})} placeholder="Email" type="email"
                               className="input-with-line w-full"/>
                        <div className="line"></div>
                    </div>
                    {errors.email && (<p>Insira o seu email.</p>)}

                    <div className="inputs relative my-4">
                        <input {...register("password", {required: true})} placeholder="Senha"
                               type={mostrarSenha ? 'text' : 'password'}
                               className="input-with-line w-full"/>
                        <div className="absolute bottom-3 right-4 text-sky-950" onClick={alternarVisibilidadeSenha}>
                            {mostrarSenha ? <FaRegEye/> : <FaRegEyeSlash/>}
                        </div>
                        <div className="line"></div>
                        {errors.password && (<p>Campo obrigatório.</p>)}
                    </div>
                    {errors.password && (<p>Insira uma senha.</p>)}

                    <div className="inputs relative my-4">
                        <input
                            {...register("verificaSenha", {required: true})}
                            placeholder="Insira a senha anterior"
                            type={mostrarSenha ? 'text' : 'password'}
                            className="input-with-line w-full"
                        />
                        <div className="absolute bottom-3 right-4 text-sky-950" onClick={alternarVisibilidadeSenha}>
                            {mostrarSenha ? <FaRegEye/> : <FaRegEyeSlash/>}
                        </div>
                        <div className="line"></div>
                    </div>
                    {errors.verificaSenha && (<p>Insira a senha anterior para verificação.</p>)}
                    {mensagemErro && (<p>{mensagemErro}</p>)}

                    <div className="w-full p-2">
                        <input type="submit"
                               className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded w-full"/>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default CadastroUsuario;