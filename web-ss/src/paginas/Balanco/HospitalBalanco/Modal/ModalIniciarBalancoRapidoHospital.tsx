import React, {useState} from 'react';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import {useForm} from "react-hook-form";
import Selector from "../../../../componentes/Selector";
import {Titulos} from "../../../../core/ENUM/Titulos";
import {
    analiseBalancoReceitaEnum,
    analiseBalancoDespesaEnum,
    analiseBalancoReceitaDespesaEnum,
    balancoEnum,
    TipoBalanco,
    analiseTipoBalancoReceitaDespesaEnum,
    AnaliseBalancoReceita,
    AnaliseBalancoDespesa,
    analiseBalancoDespesaHospital
} from "../../../../core/ENUM/TipoBalanco";
import {graficoEnum} from "../../../../core/ENUM/TipoGrafico";
import {criarBalancoRapidoDespesa} from "../../../../logica/API/BalancoDespesa";

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
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
    categoriaOuTituloContabil: string;
    categoriaOuTituloContabilDespesa: boolean;
    categoriaOuTituloContabilReceita: boolean;
}

const ModalIniciarBalancoRapidoHospital: React.FC<BasicModalProps> = ({ onClose }) => {

    const [balanco, setBalanco] = useState<string>("");
    const [erro, setErro] = useState<boolean>(false);

    const {
        register,
        handleSubmit,
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    const handleTipoBalanco = async (tipoBalanco: any) => {
        if(tipoBalanco != null) {
            if(tipoBalanco.nome === TipoBalanco.DESPESA) {
                setBalanco(tipoBalanco.nome)
                setValue('tipoBalanco', tipoBalanco.nome);
            } else if (tipoBalanco.nome === TipoBalanco.RECEITA.toString()) {
                setValue('tipoBalanco', tipoBalanco.nome);
                setBalanco(tipoBalanco.nome)
            } else if (tipoBalanco.nome === TipoBalanco.DESPESA_RECEITA.toString()) {
                setBalanco(tipoBalanco.nome)
                setValue('tipoBalanco', tipoBalanco.nome);
            } else {
                setValue('tipoBalanco', tipoBalanco.nome);
                setBalanco(tipoBalanco.nome)
            }
        }
    }

    const handleBalanco = async (tipoBalanco: any) => {
        if(tipoBalanco != null) {
            console.log(`tipo balanco: `+tipoBalanco.nome)
            setValue('analiseBalanco', tipoBalanco.nome);
            if(tipoBalanco.nome === AnaliseBalancoDespesa.BUSCAR_TODAS_DESPESAS) {
                setValue('categoriaOuTituloContabilDespesa', true)
            } else if (tipoBalanco.nome === AnaliseBalancoReceita.BUSCAR_TODAS_RECEITAS) {
                setValue('categoriaOuTituloContabilReceita', true)
            } else {
                setValue('categoriaOuTituloContabilDespesa', false)
                setValue('categoriaOuTituloContabilReceita', false)
            }
        }
    }

    const handleTipoGrafico = async (tipoGrafico: any) => {
        if(tipoGrafico != null) {
            setValue('tipoVisualizacao', tipoGrafico.nome);
        }
    }

    const handleBalancoCategoriaOuTituloContabil = async (categoriaOuTituloContabil: any) => {
        if(categoriaOuTituloContabil != null) {
            setValue('categoriaOuTituloContabil', categoriaOuTituloContabil.nome);
        }
    }

    const onSubmit = async (data: IFormInputs) =>  {
        if (
            !data.nome ||
            !data.tipoBalanco ||
            !data.analiseBalanco ||
            !data.dataInicio ||
            !data.dataTermino ||
            !data.tipoVisualizacao || (!data.tipoBalanco && !data.categoriaOuTituloContabil)
        ) {
            setErro(true)
            return;
        }
        const jsonData = {
            nome: data.nome,
            tipoBalanco: data.tipoBalanco,
            analiseBalanco: data.analiseBalanco,
            dataInicio: data.dataInicio,
            dataTermino: data.dataTermino,
            tipoVisualizacao: data.tipoVisualizacao,
            categoriaOuTituloContabil: data.categoriaOuTituloContabil
        };

        criarBalancoRapidoDespesa(jsonData)
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
                        {erro && (
                            <>
                                <div className="inputs relative my-4 bg-red-300 p-2 rounded-md border-rose-800 border-2">
                                    <p className={"text-black font-medium"}>Prencha todos os campos.</p>
                                </div>
                            </>
                        )}

                        <div className="inputs relative my-4">
                            <input
                                {...register('nome', {required: true})}
                                type="text"
                                value={watch('nome')} placeholder={Titulos.INPUT_NOME_BALANCO.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                        </div>
                        {errors.nome && (<p>Insira o nome do balanço.</p>)}

                        <div className="inputs relative my-4">
                            <Selector dado={balancoEnum}
                                      placeholder={Titulos.INPUT_TIPO_BALANCO.toString()}
                                      valorSelecionado={""} onGenericoSelect={handleTipoBalanco}/>
                            <div className="line"></div>
                        </div>

                        {balanco === "Despesa" && (
                            <>
                                <div
                                    className="inputs relative my-4">
                                    <Selector dado={analiseBalancoDespesaHospital}
                                              placeholder={Titulos.INPUT_CATEGORIA_BALANCO.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleBalanco}/>
                                    <div className="line"></div>
                                </div>

                                {watch('categoriaOuTituloContabilDespesa') === true && (
                                    <>
                                        <div
                                            className="inputs relative my-4">
                                            <Selector dado={analiseTipoBalancoReceitaDespesaEnum}
                                                      placeholder={Titulos.INPUT_TIPO_CATEGORIA_TITULO_BALANCO.toString()}
                                                      valorSelecionado={""}
                                                      onGenericoSelect={handleBalancoCategoriaOuTituloContabil}/>
                                            <div className="line"></div>
                                        </div>
                                    </>
                                )}
                            </>
                        )}

                        {balanco === "Receita" && (
                            <>
                                <div
                                    className="inputs relative my-4">
                                    <Selector dado={analiseBalancoReceitaEnum}
                                              placeholder={Titulos.INPUT_CATEGORIA_BALANCO.toString()}
                                              valorSelecionado={""} onGenericoSelect={handleBalanco}/>
                                    <div className="line"></div>
                                </div>

                                {watch('categoriaOuTituloContabilReceita') === true && (
                                    <>
                                        <div
                                            className="inputs relative my-4">
                                            <Selector dado={analiseTipoBalancoReceitaDespesaEnum}
                                                      placeholder={Titulos.INPUT_TIPO_CATEGORIA_TITULO_BALANCO.toString()}
                                                      valorSelecionado={""}
                                                      onGenericoSelect={handleBalancoCategoriaOuTituloContabil}/>
                                            <div className="line"></div>
                                        </div>
                                    </>
                                )}
                            </>
                        )}

                        <div className="inputs relative my-4">
                            <label className="text-gray-500">Data Inicio</label>
                            <input
                                {...register('dataInicio', {required: true})}
                                type="date"
                                value={watch('dataInicio') ? new Date(watch('dataInicio')).toISOString().substr(0, 10) : ''}
                                placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                        </div>
                        {errors.dataInicio && (<p>Insira a data inicial.</p>)}


                        <div className="inputs relative my-4">
                            <label className="text-gray-500">Data Final</label>
                            <input
                                {...register('dataTermino', {required: true})}
                                type="date"
                                value={watch('dataTermino') ? new Date(watch('dataTermino')).toISOString().substr(0, 10) : ''}
                                placeholder={Titulos.INPUT_DATA_RECEITA.toString()}
                                className="text-lg text-black input-with-line w-full"
                            />
                            <div className="line"></div>
                            {errors.dataTermino && (<p>Insira a data final.</p>)}
                        </div>

                        <div className="inputs relative my-4">
                            <Selector dado={graficoEnum}
                                      placeholder={Titulos.INPUT_TIPO_VISUALIZACAO_BALANCO.toString()}
                                      valorSelecionado={""} onGenericoSelect={handleTipoGrafico}/>
                            <div className="line"></div>
                        </div>

                        <div className="p-2">
                            <input
                                type="submit"
                                className="bg-secundaria_esmeralda hover:bg-bota_acao_hover text-white font-bold py-2
                                px-4 rounded w-full"
                                value="Cadastrar"
                            />
                        </div>
                    </form>

                </Box>
            </Modal>
        </div>
    );
}

export default ModalIniciarBalancoRapidoHospital;