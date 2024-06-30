import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../../componentes/header/headerPadrao";
import {Sidebar} from "../../../componentes/sidebar/sidebar";
import {Ajuda} from "../../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import {
    Box, Collapse,
    FormControl, IconButton,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table, TableBody, TableCell,
    TableContainer,
    TableHead, TablePagination, TableRow,
    TextField
} from "@mui/material";
import {MdOutlineKeyboardArrowDown, MdOutlineKeyboardArrowUp} from "react-icons/md";
import {CiEdit} from "react-icons/ci";
import {buscarTodasDespesas} from "../../../logica/API/DespesaAPI";
import {useNavigate} from "react-router-dom";
import  Loading  from "../../../componentes/Loading";

interface DataIndexable {
    [key: string]: string | number | Date | boolean | any;
}

interface Data extends DataIndexable {
    contaInterna: any;
    categoria: string;
    tituloContabil: string;
    valorDespesa: number;
    dataDespesa: Date;
    beneficiario: string;
    categoriaTransacao: string;
    bancoOrigem: string;
    dadosBancariosOrigem: string;
    bancoDestino: string;
    agenciaDestino: string;
    numeroContaDestino: string;
    descricao: string;
    tipoContaOrigem: string;
}

function Despesa() {
    const [loading, setLoading] = useState(true);
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [dadosDespesa, setDadosDespesa] = useState<any[]>([]);
    const [open, setOpen] = useState(-1);
    const [filteredData, setFilteredData] = useState<Data[]>(dadosDespesa);
    const [filterValue, setFilterValue] = useState('');
    const [filterField, setFilterField] = useState('categoriaTransacao');
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const navigate = useNavigate();

    useEffect(() => {
        setTimeout(() => {
            setLoading(false);
        }, 2000);
    }, []);

    useEffect(() => {
        const storageUser = localStorage.getItem('nomeUser');
        if (storageUser) {
            setNomeUsuario(storageUser);
        }
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [despesa] = await Promise.all([
                    buscarTodasDespesas()
                ]);

                setDadosDespesa(despesa);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    const handleCadastro = async () => {
        window.location.href = "/cadastroDespesa/";
    };

    useEffect(() => {
        setFilteredData(dadosDespesa);
    }, [dadosDespesa]);

    useEffect(() => {
        const filtered = dadosDespesa.filter(item =>
            item[filterField].toString().toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosDespesa]);

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleEdit = (id?: number) => {
        if (id) {
            navigate(`/cadastroDespesa/${id}`);
        } else {
            navigate(`/cadastroDespesa`);
        }
    };

    return (
        <div>
            {loading ? (
                <Loading/>
            ) : (
                <div>
                    <HeaderPadrao nomeUsuario={nomeUsuario}/>
                    <div className={"flex"}>
                        <Sidebar/>
                        <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                            <div className={"flex justify-between"}>
                                <h1 className={"text-2xl font-semibold"}> Listagem de Despesas e Investimentos</h1>
                                <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_DESPESA}/>
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
                                            <MenuItem value="categoriaTransacao">Origem</MenuItem>
                                            <MenuItem value="categoria">Categoria</MenuItem>
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
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Detalhes</TableCell>
                                                <TableCell
                                                    sx={{fontSize: '16px', fontWeight: 600}}>Categoria</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Título
                                                    Contábil</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Origem</TableCell>
                                                <TableCell
                                                    sx={{fontSize: '16px', fontWeight: 600}}>Beneficiário</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Data</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Valor $</TableCell>
                                                <TableCell
                                                    sx={{fontSize: '16px', fontWeight: 600}}>Descrição</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Ação</TableCell>
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
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.categoria}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.titulo_contabil}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.categoriaTransacao}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.beneficiario}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.dataFormatada}</TableCell>
                                                        <TableCell sx={{fontSize: '16px'}}>
                                                            {new Intl.NumberFormat('pt-BR', {
                                                                style: 'currency',
                                                                currency: 'BRL'
                                                            }).format(receita.valorDespesa)}
                                                        </TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{receita.descricao}</TableCell>
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
                                                                   sx={{
                                                                       paddingBottom: 0,
                                                                       paddingTop: 0,
                                                                       border: '0px'
                                                                   }}>
                                                            <Collapse in={open === index} timeout="auto" unmountOnExit>
                                                                <Box sx={{
                                                                    width: '100%',
                                                                    overflow: 'hidden',
                                                                    padding: '9px'
                                                                }}>
                                                                    <Table>
                                                                        <TableHead>
                                                                            <TableRow sx={{backgroundColor: "#f7f9fc"}}>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Conta Interna</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Tipo Conta</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Banco Origem</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Agencia Origem</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Nº Conta Origem</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Banco Destino</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Agencia Destino</TableCell>
                                                                                <TableCell sx={{
                                                                                    fontSize: '16px',
                                                                                    fontWeight: 600
                                                                                }}>Nº Conta Destino</TableCell>
                                                                            </TableRow>
                                                                        </TableHead>
                                                                        <TableBody>
                                                                            <TableRow key={receita.id}>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.contaInterna.nome}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.tipoContaOrigem}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.bancoOrigem}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.agenciaOrigem}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.numeroContaOrigem}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.bancoDestino}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.agenciaDestino}</TableCell>
                                                                                <TableCell
                                                                                    sx={{fontSize: '16px'}}>{receita.numeroContaDestino}</TableCell>
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
            )}
        </div>
    );
}

export default Despesa;

