import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../../componentes/header/headerPadrao";
import {Sidebar} from "../../../componentes/sidebar/sidebar";
import {Ajuda} from "../../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import {
    Box, FormControl,
    IconButton, InputLabel, MenuItem,
    Paper, Select, SelectChangeEvent,
    Table,
    TableBody,
    TableCell, TableContainer,
    TableHead,
    TablePagination,
    TableRow, TextField
} from "@mui/material";
import {CiEdit, CiTrash} from "react-icons/ci";
import {useNavigate} from "react-router-dom";
import {buscarCategoria} from "../../../logica/API/CategoriaAPI";

interface DataWithIndex extends Data{
    [key: string]: any;
}

interface Coluna {
    id: 'nome' | 'tipo' | 'opcoes';
    label: string;
    minWidth?: number;
    align?: 'center';
}

interface Data  {
    nome: string;
    tipo: string;
    opcoes: string;
}

const colunas: readonly Coluna[] = [
    { id: 'nome', label: 'Nome', minWidth: 10 },
    { id: 'tipo', label: 'Tipo', minWidth: 30 },
    { id: 'opcoes', label: 'Opções', minWidth: 10},
];

function TelaCategoria() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [dadosCategoria, setdadosCategoria] = useState<DataWithIndex[]>([]);
    const [filteredData, setFilteredData] = useState<DataWithIndex[]>(dadosCategoria);
    const [filterValue, setFilterValue] = useState('');
    const [filterField, setFilterField] = useState('origem');
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
                const categoria = await buscarCategoria();
                console.log("categoria: ", categoria);
                setdadosCategoria(categoria);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };
        fetchData();
    }, []);


    useEffect(() => {
        setFilteredData(dadosCategoria);
    }, [dadosCategoria]);

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilterValue(event.target.value);
    };

    const handleFilterFieldChange = (event: SelectChangeEvent<string>) => {
        setFilterField(event.target.value);
    };

    useEffect(() => {
        const filtered = filterValue.trim() === ''
            ? dadosCategoria // Se o valor do filtro estiver vazio, retorna todos os dados
            : dadosCategoria.filter(item =>
                item[filterField].toLowerCase().includes(filterValue.toLowerCase())
            );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosCategoria]);


    const handleCadastro = async () => {
        window.location.href = "/cadastroCategoria/";
    };

    function handleEdit(id: number) {

    }

    function handleDelete(id: any) {
        
    }

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Gerenciador de Categorias </h1>
                        <Ajuda tipoAjuda={AjudaEnum.GERENCIADOR_CATEGORIA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-24 rounded-md"
                            onClick={handleCadastro}>
                        Cadastrar
                    </button>

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md ml-4"
                            onClick={handleCadastro}>
                        Ver Associação
                    </button>

                    <div className="mt-10 w-full max-w-6xl ">
                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: '10px'}}>
                            <FormControl sx={{minWidth: 120, marginRight: '10px'}}>
                                <InputLabel id="filter-field-label" sx={{marginTop: '-15px'}}></InputLabel>
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
                        <TableContainer component={Paper}
                                        sx={{width: '100%', overflow: 'hidden', padding: '9px'}}>
                            <Table>
                                <TableHead>
                                    <TableRow sx={{backgroundColor: "rgba(211,211,211,.2)"}}>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Nome</TableCell>
                                        <TableCell
                                            sx={{fontSize: '16px', fontWeight: 600}}>Tipo</TableCell>
                                        <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Ação</TableCell>
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

export default TelaCategoria;