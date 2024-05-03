import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../componentes/header/headerPadrao";
import {Sidebar} from "../../componentes/sidebar/sidebar";
import Loading from "../../componentes/Loading";
import {Ajuda} from "../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../core/ENUM/Ajuda";
import {
    Box,
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
import {CiEdit} from "react-icons/ci";
import {buscarProjetos} from "../../logica/API/ProjetosAPI";
import {useNavigate} from "react-router-dom";

interface DataIndexable {
    [key: string]: string | number |  boolean | any;
}

interface Data extends DataIndexable {
    nome: string;
    categoria: string;
    valor_meta: number;
    data_inicio: string;
    data_final: string;
    descricao: string;
    valor_arrecadado_atual: number;
}

function Projetos() {
    const [loading, setLoading] = useState(true);

    const [dadosProjeto, setDadosProjeto] = useState<any[]>([]);
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [filteredData, setFilteredData] = useState<Data[]>(dadosProjeto);
    const [filterValue, setFilterValue] = useState('');
    const [filterField, setFilterField] = useState('nome');
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
                const [projetos] = await Promise.all([
                    buscarProjetos()
                ]);
                console.log("projetos: "+projetos)
                setDadosProjeto(projetos);
            } catch (error) {
                console.error('Erro ao carregar os dados', error);
            }
        };

        fetchData();
    }, []);

    useEffect(() => {
        setFilteredData(dadosProjeto);
    }, [dadosProjeto]);

    useEffect(() => {
        const filtered = dadosProjeto.filter(item =>
            item[filterField].toString().toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredData(filtered);
    }, [filterValue, filterField, dadosProjeto]);

    const handleChangePage = (event: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleCadastro = async () => {
        window.location.href = "/CadastroProjeto/";
    };

    const handleEdit = (id?: number) => {
        if (id) {
            navigate(`/CadastroProjeto/${id}`);
        } else {
            navigate(`/CadastroProjeto`);
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
                                <h1 className={"text-2xl font-semibold"}> Listagem de Projetos </h1>
                                <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_PROJETO}/>
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
                                            <MenuItem value="nome">Nome</MenuItem>
                                            <MenuItem value="valor_meta">Valor Meta</MenuItem>
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
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Categoria</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Valor Meta</TableCell>
                                                <TableCell
                                                    sx={{fontSize: '16px', fontWeight: 600}}>Valor Arrecadado</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Data Inicial</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Data Final</TableCell>
                                                <TableCell
                                                    sx={{fontSize: '16px', fontWeight: 600}}>Descrição</TableCell>
                                                <TableCell sx={{fontSize: '16px', fontWeight: 600}}>Ação</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {(rowsPerPage > 0
                                                    ? filteredData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                                    : filteredData
                                            ).map((projeto, index) => (
                                                <>
                                                    <TableRow key={projeto.id}>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{projeto.nome}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{projeto.categoria}</TableCell>

                                                        <TableCell sx={{fontSize: '16px'}}>
                                                            {new Intl.NumberFormat('pt-BR', {
                                                                style: 'currency',
                                                                currency: 'BRL'
                                                            }).format(projeto.valor_meta)}
                                                        </TableCell>

                                                        <TableCell sx={{fontSize: '16px'}}>
                                                            {new Intl.NumberFormat('pt-BR', {
                                                                style: 'currency',
                                                                currency: 'BRL'
                                                            }).format(projeto.valor_arrecadado_atual)}
                                                        </TableCell>

                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{projeto.dataFormatadaInicio}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{projeto.dataFormatadaFinal}</TableCell>
                                                        <TableCell
                                                            sx={{fontSize: '16px'}}>{projeto.descricao}</TableCell>

                                                        <TableCell key={projeto.id} align={projeto.align}>
                                                            <IconButton
                                                                aria-label="Editar"
                                                                onClick={() => handleEdit(projeto.id)}
                                                            >
                                                                <CiEdit/>
                                                            </IconButton>
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

export default Projetos;