import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../../componentes/header/headerPadrao";
import { Sidebar } from "../../../componentes/sidebar/sidebar";
import { Ajuda } from "../../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../../core/ENUM/Ajuda";
import ModalIniciarBalancoRapido from "../LancamentoBalanco/Modal/ModalIniciarBalancoRapido";
import ModalCadastrarBalanco from "./Modal/ModalCadastrarBalanco";
import {
    Box,
    FormControl,
    IconButton, InputLabel, MenuItem,
    Paper, Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow, TextField
} from "@mui/material";
import {CiEdit, CiTrash} from "react-icons/ci";
import { IoEyeOutline } from "react-icons/io5";
import {buscarBalanco, buscarBalancoPorId, deletarBalancoPorId} from "../../../logica/API/BalancoAPI";
import {useForm} from "react-hook-form";
import {criarBalancoRapidoDespesa} from "../../../logica/API/BalancoDespesa";

interface DataIndexable {
    [key: string]: string | Date | number |  boolean | any;
}

interface Data extends DataIndexable {
    nome: string;
    tipoBalanco: string;
    analise_balanco: string;
    dataInicio: Date;
    dataTermino: Date;
    dataInicios: string;
    dataTerminos: string;
    tipoVisualizacao: string;
    categoriaOuTituloContabil: number;
}

class IFormInputs {
    id: any;
}

function LancamentoBalanco() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [modalAberto, setModalAberto] = useState<boolean>(false);
    const [modalSalvarBalancoAberto, setSalvarBalancoAberto] = useState<boolean>(false);
    const [dadosBalanco, setDadosBalanco] = useState<any[]>([]);
    const [filteredData, setFilteredData] = useState<Data[]>(dadosBalanco);
    const [filterValue, setFilterValue] = useState('');
    const [filterField, setFilterField] = useState('nome');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const {
        setValue,
        formState: { errors },
        watch,
    } = useForm<IFormInputs>();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [balanco] = await Promise.all([
                    buscarBalanco()
                ]);

                setDadosBalanco(balanco);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    function handleCadastro() {
        setSalvarBalancoAberto(true)
    }

    function handleCadastroFechado() {
        setSalvarBalancoAberto(false)
    }

    function handleAbrirModal() {
        setModalAberto(true);
    }

    function handleFecharModal() {
        setModalAberto(false);
    }

    useEffect(() => {
        setFilteredData(dadosBalanco);
    }, [dadosBalanco]);

    useEffect(() => {
        const filtered = dadosBalanco.filter(item =>
            item[filterField].toString().toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosBalanco]);

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    function handleEdit(id: any) {
        if (id) {
            setValue('id',id)
            setSalvarBalancoAberto(true)
        }
    }

    function handleApagar(id: any) {
        deletarBalancoPorId(id)
    }

    async function VisualizarBalanco(id: any) {
        try {
            buscarBalancoPorId(id)
                .then(response => {
                    console.log(response);
                    const jsonData = {
                        nome: response.nome,
                        tipoBalanco: response.tipoBalanco,
                        analiseBalanco: response.analise_balanco,
                        dataInicio: response.data_inicio,
                        dataTermino: response.data_termino,
                        tipoVisualizacao: response.tipo_visualizacao,
                        categoriaOuTituloContabil: response.categoria_titulo_contabil
                    };

                    criarBalancoRapidoDespesa(jsonData)
                })
                .catch(error => {
                    console.error('Erro ao buscar dados do balanço:', error);
                });
        } catch (error) {
            console.error('Erro ao buscar dados do balanço:', error);
        }
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />

            <div className={"flex"}>
                <Sidebar />
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Balanços de Lançamentos </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_BALANCO_LANCAMENTO} />
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"} />

                    <div className="mt-4 w-full max-w-6xl ">

                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md"
                                onClick={handleCadastro}>
                            Criar Balanço
                        </button>

                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md ml-4"
                                onClick={handleAbrirModal}>
                            Balanço Rápido
                        </button>

                        {modalAberto && <ModalIniciarBalancoRapido onClose={handleFecharModal} />}

                        {modalSalvarBalancoAberto && <ModalCadastrarBalanco onClose={handleCadastroFechado} id={watch('id')} />}


                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: '10px', marginTop: '30px'}}>
                            <FormControl sx={{minWidth: 120, marginRight: '10px'}}>
                                <InputLabel id="filter-field-label" sx={{marginTop: '-15px'}}></InputLabel>
                                <Select
                                    labelId="filter-field-label"
                                    id="filter-field-select"
                                    value={filterField}
                                    onChange={(e) => setFilterField(e.target.value as string)}
                                >
                                    <MenuItem value="nome">Nome</MenuItem>
                                    <MenuItem value="tipoBalanco">Tipo Balanco</MenuItem>
                                </Select>
                            </FormControl>
                            <TextField
                                label="Filtrar"
                                value={filterValue}
                                onChange={(e) => setFilterValue(e.target.value)}
                            />
                        </Box>
                        <TableContainer component={Paper}
                                        sx={{width: '100%', overflow: 'hidden', padding: '9px'}}>
                            <Table>
                                <TableHead>
                                    <TableRow sx={{backgroundColor: "rgba(211,211,211,.2)"}}>
                                        <TableCell
                                            sx={{fontSize: '16px', fontWeight: 600}}>Nome</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Tipo Balanço</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Análise Balanço</TableCell>
                                        <TableCell
                                            sx={{fontSize: '16px', fontWeight: 600}}>Data Início</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Data Final</TableCell>
                                        <TableCell
                                            sx={{fontSize: '16px', fontWeight: 600}}>Tipo Visualização</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Categoria/Título</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Ação</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {(rowsPerPage > 0
                                            ? filteredData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                            : filteredData
                                    ).map((balanco, index) => (
                                        <>
                                            <TableRow key={balanco.id}>
                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.nome}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.tipoBalanco}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.analise_balanco}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.data_inicio_balanco}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.data_final_balanco}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.tipo_visualizacao}</TableCell>

                                                <TableCell
                                                    sx={{fontSize: '16px'}}>{balanco.categoria_titulo_contabil}</TableCell>

                                                <TableCell key={balanco.id} align={balanco.align}>
                                                            <span style={{ display: 'flex' }}>
                                                                <IconButton
                                                                    aria-label="Editar"
                                                                    onClick={() => handleEdit(balanco.id)}
                                                                >
                                                                    <CiEdit/>
                                                                </IconButton>
                                                                <IconButton
                                                                    aria-label="Apagar"
                                                                    onClick={() => handleApagar(balanco.id)}
                                                                >
                                                                    <CiTrash/>
                                                                </IconButton>
                                                                <IconButton
                                                                    aria-label="Visualizar Balanço"
                                                                    onClick={() => VisualizarBalanco(balanco.id)}
                                                                >
                                                                    <IoEyeOutline/>
                                                                </IconButton>
                                                            </span>
                                                </TableCell>

                                            </TableRow>

                                        </>
                                    ))}
                                </TableBody>
                            </Table>
                            <TablePagination
                                rowsPerPageOptions={[5, 10, 25]}
                                component="div"
                                count={filteredData.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                labelRowsPerPage=""
                            />
                        </TableContainer>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LancamentoBalanco;
