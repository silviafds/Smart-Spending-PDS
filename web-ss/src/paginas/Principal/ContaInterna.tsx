import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import axios from '../../core/contexto/axios';
import { BACKEND_URL } from "../../core/config";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import IconButton from '@mui/material/IconButton';
import TextField from '@mui/material/TextField';
import { CiEdit, CiTrash } from "react-icons/ci";
import 'tailwindcss/tailwind.css';
import Swal from "sweetalert2";
import {ContaInternaDTO} from "../../core/DTO/ContaInternaDTO";

interface Column {
    id: 'id' | 'nome' | 'opcoes' | 'desabilitarConta';
    label: string;
    minWidth?: number;
    align?: 'right';
}

interface Data {
    id: number;
    nome: string;
    opcoes: string;
    desabilitarConta: boolean;
}

const columns: readonly Column[] = [
    { id: 'id', label: 'Código', minWidth: 10},
    { id: 'nome', label: 'Nome', minWidth: 30},
    { id: 'opcoes', label: 'Opções', minWidth: 10},
    { id: 'desabilitarConta', label: 'Desabilitar', minWidth: 10},
];

function ContaInterna() {
    const [rows, setRows] = useState<Data[]>([]);
    const [filteredRows, setFilteredRows] = useState<Data[]>([]);
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [filterValue, setFilterValue] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${BACKEND_URL}/contaInterna/buscarConta`);
                setRows(response.data);
                setFilteredRows(response.data);
            } catch (error) {
                console.error('Erro ao carregar os dados da tabela:', error);
            }
        };
        fetchData();
    }, []);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    const deleteRow = (id: number) => {
        const updatedRows = rows.filter(row => row.id !== id);
        setRows(updatedRows);
        setFilteredRows(updatedRows);
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    useEffect(() => {
        const filteredData = rows.filter((row) =>
            row.id.toString().includes(filterValue) ||
            row.nome.toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredRows(filteredData);
    }, [filterValue, rows]);

    const handleCadastro = async () => {
        window.location.href = "/cadastroContaInterna/";
    };

    const handleEdit = (id: number) => {
        navigate(`/editarContaInterna/${id}`);
    };

    const handleDelete = (id: number): Promise<void> => {
        return Swal.fire({
            title: "Deseja apagar conta?",
            text: "A exclusão de uma conta interna só é possível se não existirem dados vinculados a ela.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#029d02",
            cancelButtonColor: "#D60000",
            confirmButtonText: "Ok, deletar conta."
        }).then((result) => {
            if (result.isConfirmed) {
                return axios.delete(`${BACKEND_URL}/contaInterna/deletarConta/${id}`)
                    .then(() => {
                        deleteRow(id)
                        Swal.fire({
                            title: "Conta deletada!",
                            icon: "success",
                            showConfirmButton: false,
                        });
                    })
                    .catch((error) => {
                        if (error.response && error.response.status === 400) {
                            const responseData = error.response.data;
                            console.log('Erro de BadRequest:', responseData);
                            Swal.fire({
                                icon: "error",
                                title: "Não foi possível deletar",
                                text: responseData.message,
                                customClass: {
                                    confirmButton: 'error-button'
                                }
                            });
                        } else if (error.request) {
                            console.log("request: ", error.request);
                        } else {
                            console.log('Error', error.message);
                        }
                        console.log(error.config);
                    });
            }
        });
    };

    const handleCheckboxChange = async (event: React.ChangeEvent<HTMLInputElement>, id: number, nome: string) => {
        const { checked } = event.target;
        const dto = new ContaInternaDTO(nome);
        dto.id = Number(id);
        dto.desabilitarConta = checked;
        const payload = {
            id: dto.id,
            nome: dto.nome,
            desabilitarConta: dto.desabilitarConta
        }

        const axiosConfig = { headers: { 'Content-Type': 'application/json' } };
            await axios.patch(BACKEND_URL + "/contaInterna/editarStatusConta", payload, axiosConfig)
                .then((response) => {
                    const updatedRows = rows.map(row => {  //atualização de checkbox na tabela
                        if (row.id === id) {
                            return { ...row, desabilitarConta: checked }; // Atualiza o estado desabilitarConta
                        }
                        return row;
                    });
                    setFilteredRows(updatedRows); // Atualiza as linhas filtradas
                    setRows(updatedRows); // Atualiza o estado das linhas

                })
                .catch(function (error) {
                    if (error.response) {
                        Swal.fire({
                            icon: "error",
                            title: "Ocorreu um erro ao desabilitar/habilitar conta interna.",
                            showConfirmButton: true,
                            confirmButtonColor: "#b00303",
                            confirmButtonText: "OK",
                            customClass: {
                                confirmButton: "bg-sky-950",
                            },
                        });
                    } else if (error.request) {
                        console.log(error.request);
                    } else {
                        console.log('Error', error.message);
                    }
                    console.log(error.config);
                });
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen h-20 p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Listagem de Contas Internas </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_CONTA_INTERNA}/>
                    </div>
                    <button className="bg-principal hover:bg-sky-800 mt-10 text-white p-2 w-24 rounded-md"
                            onClick={handleCadastro}>
                        Cadastrar
                    </button>
                    <div className="mt-10 w-full max-w-6xl ">
                        <Paper sx={{ width: '100%', overflow: 'hidden', padding: '9px' }}>
                            <TextField
                                label="Filtrar"
                                value={filterValue}
                                onChange={(e) => setFilterValue(e.target.value)}
                            />
                            <TableContainer sx={{ maxHeight: 400 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            {columns.map((column) => (
                                                <TableCell
                                                    key={column.id}
                                                    align={column.align}
                                                    style={{ width: column.minWidth }}
                                                >
                                                    {column.label}
                                                </TableCell>
                                            ))}
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {filteredRows
                                            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                            .map((row) => (
                                                <TableRow hover role="checkbox" tabIndex={-1} key={row.id}>
                                                    {columns.map((column) => {
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
                                                        } else if (column.id === 'desabilitarConta') {
                                                            return (
                                                                <TableCell key={column.id} align={column.align}>
                                                                    <input
                                                                        type="checkbox"
                                                                        checked={row.desabilitarConta}
                                                                        onChange={(event) => handleCheckboxChange(event, row.id, row.nome)}
                                                                    />
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
                            </TableContainer>
                            <TablePagination
                                rowsPerPageOptions={[10, 25, 100]}
                                component="div"
                                count={filteredRows.length}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                                labelRowsPerPage=""
                            />
                        </Paper>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ContaInterna;
