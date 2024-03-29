import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import {
    Box,
    FormControl,
    IconButton,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    TextField
} from "@mui/material";
import { CiEdit, CiTrash } from "react-icons/ci";
import { useNavigate } from "react-router-dom";
import { buscarContaBancaria, deletarContaBancaria } from "../../logica/API/ContaBancaria/ContaBancariaAPI";
import { SelectChangeEvent } from '@mui/material';

interface DataWithIndex extends Data {
    [key: string]: any;
}

interface Coluna {
    id: 'nomeBanco' | 'tipoConta' | 'agencia' | 'numeroConta' | 'opcoes';
    label: string;
    minWidth?: number;
    align?: 'center';
}

interface Data {
    nomeBanco: string;
    tipoConta: string;
    agencia: string;
    numeroConta: string;
    opcoes: string;
}

const colunas: readonly Coluna[] = [
    { id: 'nomeBanco', label: 'Nome', minWidth: 10 },
    { id: 'tipoConta', label: 'Tipo', minWidth: 30 },
    { id: 'agencia', label: 'Agência', minWidth: 10 },
    { id: 'numeroConta', label: 'Número Conta', minWidth: 10 },
    { id: 'opcoes', label: 'Opções', minWidth: 10},
];

function ContaBancaria() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [dadosBancarios, setBancarios] = useState<DataWithIndex[]>([]);
    const [filteredData, setFilteredData] = useState<DataWithIndex[]>([]);
    const [filterValue, setFilterValue] = useState('');
    const [filterField, setFilterField] = useState('nomeBanco');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const navigate = useNavigate();

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const dados = await buscarContaBancaria();
                setBancarios(dados);
                setFilteredData(dados);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    const handleCadastro = () => {
        navigate("/cadastroContaBancaria");
    };

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleEdit = (id: number) => {
        navigate(`/EditarContaBancaria/${id}`);
    };

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilterValue(event.target.value);
    };

    const handleFilterFieldChange = (event: SelectChangeEvent<string>) => {
        setFilterField(event.target.value);
    };

    useEffect(() => {
        const filtered = filterValue.trim() === ''
            ? dadosBancarios // Se o valor do filtro estiver vazio, retorna todos os dados
            : dadosBancarios.filter(item =>
                item[filterField].toLowerCase().includes(filterValue.toLowerCase())
            );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosBancarios]);


    async function handleDelete(id: number) {
        await deletarContaBancaria(id);
        const dadosAtualizados = await buscarContaBancaria();
        setBancarios(dadosAtualizados);
        setFilteredData(dadosAtualizados);
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario} />
            <div className={"flex"}>
                <Sidebar />
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Listagem de Contas Bancárias </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_CONTA_BANCARIA} />
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"} />

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-24 rounded-md"
                            onClick={handleCadastro}>
                        Cadastrar
                    </button>

                    <div className="mt-10 w-full max-w-6xl ">
                        <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                            <FormControl sx={{ minWidth: 120, marginRight: '10px' }}>
                                <InputLabel id="filter-field-label" sx={{ marginTop: '-15px' }}></InputLabel>
                                <Select
                                    labelId="filter-field-label"
                                    id="filter-field-select"
                                    value={filterField}
                                    onChange={handleFilterFieldChange}
                                >
                                    {colunas.map((column) => (
                                        <MenuItem key={column.id} value={column.id}>{column.label}</MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                            <TextField
                                label="Filtrar"
                                value={filterValue}
                                onChange={handleFilterChange}
                            />
                        </Box>
                        <TableContainer component={Paper} sx={{ width: '100%', overflow: 'hidden', padding: '9px' }}>
                            <Table>
                                <TableHead>
                                    <TableRow sx={{ backgroundColor: "rgba(211,211,211,.2)" }}>
                                        {colunas.map((column) => (
                                            <TableCell
                                                key={column.id}
                                                style={{ width: column.minWidth }}
                                            >
                                                {column.label}
                                            </TableCell>
                                        ))}
                                    </TableRow>
                                </TableHead>

                                <TableBody>
                                    {filteredData
                                        .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                        .map((row, index) => (
                                            <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                                {colunas.map((column) => {
                                                    if (column.id === 'opcoes') {
                                                        return (
                                                            <TableCell key={column.id} align={column.align}>
                                                                <IconButton
                                                                    aria-label="Editar"
                                                                    onClick={() => handleEdit(row.id)}
                                                                >
                                                                    <CiEdit/>
                                                                </IconButton>
                                                                <IconButton
                                                                    aria-label="Apagar"
                                                                    onClick={() => {
                                                                        handleDelete(row.id)
                                                                    }}
                                                                >
                                                                    <CiTrash/>
                                                                </IconButton>

                                                            </TableCell>
                                                        );
                                                    } else {
                                                        const value = row[column.id];
                                                        return (
                                                            <TableCell key={column.id} align={column.align}>
                                                                {value}
                                                            </TableCell>
                                                        );
                                                    }
                                                })}
                                            </TableRow>
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

export default ContaBancaria;