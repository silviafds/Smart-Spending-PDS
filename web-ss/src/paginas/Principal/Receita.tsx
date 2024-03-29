import React, { useEffect, useState } from "react";
import { HeaderPadrao } from "../../componentes/header/headerPadrao";
import { Sidebar } from "../../componentes/sidebar/sidebar";
import { Ajuda } from "../../componentes/ajuda/Ajuda";
import { AjudaEnum } from "../../core/ENUM/Ajuda";
import {
    Box,
    Collapse,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    TablePagination
} from "@mui/material";
import { MdOutlineKeyboardArrowDown } from "react-icons/md";
import { MdOutlineKeyboardArrowUp } from "react-icons/md";
import { buscarTodasReceitas } from "../../logica/API/Receita/ReceitaAPI";
import {CiEdit} from "react-icons/ci";
import {useNavigate} from "react-router-dom";

interface DataIndexable {
    [key: string]: string | number | Date | boolean | any;
}

interface Data extends DataIndexable {
    id: number;
    contaInterna: any;
    categoria: string;
    titulo_contabil: string;
    valorReceita: number;
    dataFormatada: string;
    pagador: string;
    origem: string;
    bancoOrigem: string;
    agenciaOrigem: string;
    numeroContaOrigem: string;
    bancoDestino: string;
    tipoContaDestino: string;
    agenciaDestino: string;
    numeroContaDestino: string;
    descricao: string;
}

function Receita() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [dadosReceita, setDadosReceita] = useState<any[]>([]);
    const [open, setOpen] = useState(-1);
    const [filteredData, setFilteredData] = useState<Data[]>(dadosReceita);
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
                const [receita] = await Promise.all([
                    buscarTodasReceitas()
                ]);

                setDadosReceita(receita);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    const handleCadastro = async () => {
        window.location.href = "/cadastroReceita/";
    };

    useEffect(() => {
        setFilteredData(dadosReceita);
    }, [dadosReceita]);

    useEffect(() => {
        const filtered = dadosReceita.filter(item =>
            item[filterField].toString().toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosReceita]);

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleEdit = (id?: number) => {
        if (id) {
            navigate(`/cadastroReceita/${id}`);
        } else {
            navigate(`/cadastroReceita`);
        }
    };


    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>
            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Listagem de Receitas </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_RECEITA}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <button className="bg-principal hover:bg-sky-800 text-white p-2 w-24 rounded-md"
                            onClick={handleCadastro}>
                        Cadastrar
                    </button>

                    <div className="mt-10 w-full max-w-6xl ">
                        <Box sx={{display: 'flex', alignItems: 'center', marginBottom: '10px'}}>
                            <FormControl sx={{minWidth: 120, marginRight: '10px'}}>
                                <InputLabel id="filter-field-label" sx={{marginTop: '-15px'}}></InputLabel>
                                <Select
                                    labelId="filter-field-label"
                                    id="filter-field-select"
                                    value={filterField}
                                    onChange={(e) => setFilterField(e.target.value as string)}
                                >
                                    <MenuItem value="origem">Origem</MenuItem>
                                    <MenuItem value="categoria">Categoria</MenuItem>
                                </Select>
                            </FormControl>
                            <TextField
                                label="Filtrar"
                                value={filterValue}
                                onChange={(e) => setFilterValue(e.target.value)}
                            />
                        </Box>
                        <TableContainer component={Paper} sx={{width: '100%', overflow: 'hidden', padding: '9px'}}>
                            <Table>
                                <TableHead>
                                    <TableRow sx={{backgroundColor: "rgba(211,211,211,.2)"}}>
                                        <TableCell>Detalhes</TableCell>
                                        <TableCell>Categoria</TableCell>
                                        <TableCell>Título Contábil</TableCell>
                                        <TableCell>Origem</TableCell>
                                        <TableCell>Pagador</TableCell>
                                        <TableCell>Data</TableCell>
                                        <TableCell>Valor $</TableCell>
                                        <TableCell>Descrição</TableCell>
                                        <TableCell>Ação</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {(rowsPerPage > 0
                                            ? filteredData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                            : filteredData
                                    ).map((receita, index) => (
                                        <>
                                            <TableRow key={receita.id}>
                                                <TableCell>
                                                    <IconButton
                                                        aria-label="expand row"
                                                        size="small"
                                                        onClick={() => setOpen(open === index ? -1 : index)}
                                                    >
                                                        {open === index ? (
                                                            <MdOutlineKeyboardArrowUp/>
                                                        ) : (
                                                            <MdOutlineKeyboardArrowDown/>
                                                        )}
                                                    </IconButton>
                                                </TableCell>
                                                <TableCell>{receita.categoria}</TableCell>
                                                <TableCell>{receita.titulo_contabil}</TableCell>
                                                <TableCell>{receita.origem}</TableCell>
                                                <TableCell>{receita.pagador}</TableCell>
                                                <TableCell>{receita.dataFormatada}</TableCell>
                                                <TableCell>
                                                    {new Intl.NumberFormat('pt-BR', {
                                                        style: 'currency',
                                                        currency: 'BRL'
                                                    }).format(receita.valorReceita)}
                                                </TableCell>
                                                <TableCell>{receita.descricao}</TableCell>
                                                <TableCell key={receita.id} align={receita.align}>
                                                    <IconButton
                                                        aria-label="Editar"
                                                        onClick={() => handleEdit(receita.id)}
                                                    >
                                                        <CiEdit/>
                                                    </IconButton>
                                                </TableCell>
                                            </TableRow>
                                            <TableRow>
                                                <TableCell colSpan={7}
                                                           sx={{paddingBottom: 0, paddingTop: 0, border: '0px'}}>
                                                    <Collapse in={open === index} timeout="auto" unmountOnExit>
                                                        <Box sx={{width: '100%', overflow: 'hidden', padding: '9px'}}>
                                                            <Table>
                                                                <TableHead>
                                                                    <TableRow sx={{backgroundColor: "#f7f9fc"}}>
                                                                        <TableCell>Conta Interna</TableCell>
                                                                        <TableCell>Banco Origem</TableCell>
                                                                        <TableCell>Agencia Origem</TableCell>
                                                                        <TableCell>Nº Conta Origem</TableCell>
                                                                        <TableCell>Banco Destino</TableCell>
                                                                        <TableCell>Tipo Conta</TableCell>
                                                                        <TableCell>Agencia Destino</TableCell>
                                                                        <TableCell>Nº Conta Destino</TableCell>
                                                                    </TableRow>
                                                                </TableHead>
                                                                <TableBody>
                                                                    <TableRow key={receita.id}>
                                                                        <TableCell>{receita.contaInterna.nome}</TableCell>
                                                                        <TableCell>{receita.bancoOrigem}</TableCell>
                                                                        <TableCell>{receita.agenciaOrigem}</TableCell>
                                                                        <TableCell>{receita.numeroContaOrigem}</TableCell>
                                                                        <TableCell>{receita.bancoDestino}</TableCell>
                                                                        <TableCell>{receita.tipoContaDestino}</TableCell>
                                                                        <TableCell>{receita.agenciaDestino}</TableCell>
                                                                        <TableCell>{receita.numeroContaDestino}</TableCell>
                                                                    </TableRow>
                                                                </TableBody>
                                                            </Table>
                                                        </Box>
                                                    </Collapse>
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

export default Receita;