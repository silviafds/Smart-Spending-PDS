import React, {useState} from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import {useForm} from "react-hook-form";
import Selector from "../../../componentes/Selector";
import {Titulos} from "../../../core/ENUM/Titulos";
import {analiseBalancoReceitaEnum, analiseBalancoDespesaEnum, analiseBalancoReceitaDespesaEnum,
    balancoEnum, TipoBalanco} from "../../../core/ENUM/TipoBalanco";
import {graficoEnum, TipoGrafico} from "../../../core/ENUM/TipoGrafico";

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid white',
    boxShadow: 24,
    p: 4,
    borderRadius: '5px',
};

interface BasicModalProps {
    onClose: () => void;
}

interface IFormInputs {
    nome: string;
    tipoBalanco: string;
    analiseBalanco: string;
    dataInicio: Date;
    dataTermino: Date;
    tipoVisualizacao: string;
}

const BasicModal: React.FC<BasicModalProps> = ({ onClose }) => {

    const [balanco, setBalanco] = useState<string>("");
    const [dataInicio, setDataInicio] = useState('');
    const [isFocused, setIsFocused] = useState(false);

    const handleFocus = () => {
        setIsFocused(true);
    };

    const handleBlur = () => {
        setIsFocused(false);
    };

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    function onSubmit() {

    }

    const handleTipoBalanco = async (tipoBalanco: any) => {
        if(tipoBalanco != null) {
            setValue('tipoBalanco', tipoBalanco.nome);
            if(tipoBalanco.nome === TipoBalanco.DESPESA) {
                setBalanco(tipoBalanco.nome)
                console.log("tipo do balanço selecionado: "+tipoBalanco.nome)
            } else if (tipoBalanco.nome === TipoBalanco.RECEITA.toString()) {
                setBalanco(tipoBalanco.nome)
                console.log("tipo do balanço selecionado: " + tipoBalanco.nome)
            } else if (tipoBalanco.nome === TipoBalanco.DESPESA_RECEITA.toString()) {
                setBalanco(tipoBalanco.nome)
                console.log("tipo do balanço selecionado: "+tipoBalanco.nome)
            } else {
                setBalanco(tipoBalanco.nome)
                console.log("tipo do balanço selecionado: "+tipoBalanco.nome)
            }
        }
    }

    const handleBalanco = async (tipoBalanco: any) => {
        if(tipoBalanco != null) {
            console.log("analise de balanço: "+tipoBalanco.nome)
            setValue('analiseBalanco', tipoBalanco.nome);
        }
    }

    const handleTipoGrafico = async (tipoGrafico: any) => {
        if(tipoGrafico != null) {
            setValue('tipoVisualizacao', tipoGrafico.nome);
        }
    }

    return (
        <div>
            <Modal
                open={true}
                onClose={onClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>

                    <form onSubmit={handleSubmit(onSubmit)} className="w-full">
                        <div className="inputs relative my-4"> {/*NOME DO BALANÇO*/}
                            <input
                                {...register('nome', {required: true})}
                                type="text"
                                value={watch('nome')} placeholder={Titulos.INPUT_NOME_BALANCO.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                        </div>

                        <div className="inputs relative my-4">{/*TIPO (LANÇAMENTO OU PROJETOS)*/}
                            <Selector dado={balancoEnum}
                                      placeholder={Titulos.INPUT_TIPO_BALANCO.toString()}
                                      valorSelecionado={""} onGenericoSelect={handleTipoBalanco}/>
                            <div className="line"></div>
                        </div>

                        {balanco === "Despesa" && (
                            <>
                                <div
                                    className="inputs relative my-4">{/*TIPO DE VISUALIZAÇÃO (LANÇAMENTO OU PROJETOS)*/}
                                    <Selector dado={analiseBalancoDespesaEnum}
                                              placeholder={Titulos.INPUT_TIPO_BALANCO.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleBalanco}/>
                                    <div className="line"></div>
                                </div>
                            </>
                        )}

                        {balanco === "Receita" && (
                            <>
                                <div
                                    className="inputs relative my-4">{/*TIPO DE VISUALIZAÇÃO (LANÇAMENTO OU PROJETOS)*/}
                                    <Selector dado={analiseBalancoReceitaEnum}
                                              placeholder={Titulos.INPUT_TIPO_BALANCO.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleBalanco}/>
                                    <div className="line"></div>
                                </div>
                            </>
                        )}

                        {balanco === "Despesa e Receita" && (
                            <>
                                <div
                                    className="inputs relative my-4">{/*TIPO DE VISUALIZAÇÃO (LANÇAMENTO OU PROJETOS)*/}
                                    <Selector dado={analiseBalancoReceitaDespesaEnum}
                                              placeholder={Titulos.INPUT_TIPO_BALANCO.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleBalanco}/>
                                    <div className="line"></div>
                                </div>
                            </>
                        )}

                        <div className="inputs relative my-4">
                            <label className="text-gray-500">Data Inicio</label>
                            <input
                                {...register('dataInicio', {required: false})}
                                type="date"
                                value={watch('dataInicio') ? new Date(watch('dataInicio')).toISOString().substr(0, 10) : ''}
                                placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                        </div>

                        <div className="inputs relative my-4">
                            <label className="text-gray-500">Data Final</label>
                            <input
                                {...register('dataTermino', {required: false})}
                                type="date"
                                value={watch('dataTermino') ? new Date(watch('dataTermino')).toISOString().substr(0, 10) : ''}
                                placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                        </div>

                        <div className="inputs relative my-4">{/*TIPO DE VISUALIZAÇÃO (LANÇAMENTO OU PROJETOS)*/}
                            <Selector dado={graficoEnum}
                                      placeholder={Titulos.INPUT_TIPO_VISUALIZACAO_BALANCO.toString()}
                                      valorSelecionado={""} onGenericoSelect={handleTipoGrafico}/>
                            <div className="line"></div>
                        </div>
                    </form>

                    <Button className={"text-red-500"} onClick={onClose}>Cancelar</Button>
                </Box>
            </Modal>
        </div>
    );
}

export default BasicModal;
