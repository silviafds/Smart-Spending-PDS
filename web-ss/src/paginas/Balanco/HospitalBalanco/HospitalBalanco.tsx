import React, {useEffect, useState} from "react";
import {HeaderPadrao} from "../../../componentes/header/headerPadrao";
import {Sidebar} from "../../../componentes/sidebar/sidebar";
import {Ajuda} from "../../../componentes/ajuda/Ajuda";
import {AjudaEnum} from "../../../core/ENUM/Ajuda";
import {useForm} from "react-hook-form";
import ModalIniciarBalancoRapidoHospital from "./Modal/ModalIniciarBalancoRapidoHospital";
import ModalCadastrarBalancoHosp from "./Modal/ModalCadastrarBalancoHosp";
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
import {CiEdit, CiTrash} from "react-icons/ci";
import {IoEyeOutline} from "react-icons/io5";
import {
    buscarBalanco, buscarBalancoHospital,
    buscarBalancoPorId,
    deletarBalancoPorId,
    editarBalancoDashboard
} from "../../../logica/API/BalancoAPI";
import {criarBalancoRapidoDespesa} from "../../../logica/API/BalancoDespesa";
import {DashboardDTO} from "../../../core/DTO/DashboardDTO";
import {
    adicionarBalancoAoDashboard,
    listaBalancoDash,
    removerBalancoDoDashboard
} from "../../../logica/API/DashboardAPI";

interface DataIndexable {
    [key: string]: string | Date | number | boolean | any;
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
    dashboard_check: boolean;
}

class IFormInputs {
    id: any;
}

function HospitalBalanco() {
    const [nomeUsuario, setNomeUsuario] = useState<string>("");
    const [modalAberto, setModalAberto] = useState<boolean>(false);
    const [modalSalvarBalancoAberto, setSalvarBalancoAberto] = useState<boolean>(false);
    const [filterField, setFilterField] = useState('nome');
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [page, setPage] = useState(0);
    const [filterValue, setFilterValue] = useState('');
    const [dadosBalanco, setDadosBalanco] = useState<any[]>([]);
    const [filteredData, setFilteredData] = useState<Data[]>(dadosBalanco);
    const [dadosBalancoDashboard, setDadosBalancoDashboard] = useState<any[]>([]);

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
                const [balanco, lista] = await Promise.all([
                    buscarBalancoHospital(),
                    listaBalancoDash()
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
            setValue('id', id)
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

    const handleCheckboxChange = async (event: React.ChangeEvent<HTMLInputElement>, id: number)  => {
        console.log("isdashboard: "+watch());
        const { checked } = event.target;
        const dto = new DashboardDTO();
        dto.identicador_balanco = Number(id);
        dto.isDashboard = checked;
        const payload = {
            identicador_balanco: dto.identicador_balanco,
            dashboard_check: dto.dashboard
        }
        console.log("dados payload: "+payload.dashboard_check+" | id: "+payload.identicador_balanco);

        if(payload.dashboard_check) {
            console.log("eita")
            await adicionarBalancoAoDashboard(payload);
            await editarBalancoDashboard(payload);
        }

        if(payload.dashboard_check === false) {
            console.log("remover chek")
            await removerBalancoDoDashboard(payload);
            await editarBalancoDashboard(payload);
        }
    };

    return (
        <div>
            <HeaderPadrao nomeUsuario={nomeUsuario}/>

            <div className={"flex"}>
                <Sidebar/>
                <div className={"border-solid border border-b-stone-200 w-screen p-7"}>
                    <div className={"flex justify-between"}>
                        <h1 className={"text-2xl font-semibold"}> Balanços de Hospital </h1>
                        <Ajuda tipoAjuda={AjudaEnum.DEFINICAO_ANALISE}/>
                    </div>
                    <hr className={"my-4 mt-6 p-0 w-full border-gray-300"}/>

                    <div className="mt-10 w-full max-w-6xl ">
                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md"
                                onClick={handleCadastro}>
                            Criar Balanço
                        </button>

                        <button className="bg-principal hover:bg-sky-800 text-white p-2 w-38 rounded-md ml-4"
                                onClick={handleAbrirModal}>
                            Balanço Rápido
                        </button>

                        {modalAberto && <ModalIniciarBalancoRapidoHospital onClose={handleFecharModal} />}

                        {modalSalvarBalancoAberto && <ModalCadastrarBalancoHosp onClose={handleCadastroFechado} id={watch('id')} />}

                        <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: '10px', marginTop: '30px' }}>
                            <FormControl sx={{ minWidth: 120, marginRight: '10px' }}>
                                <InputLabel id="filter-field-label" sx={{ marginTop: '-15px' }}></InputLabel>
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
                                        sx={{ width: '100%', overflow: 'hidden', padding: '9px' }}>
                            <Table>
                                <TableHead>
                                    <TableRow sx={{ backgroundColor: "rgba(211,211,211,.2)" }}>
                                        <TableCell
                                            sx={{ fontSize: '16px', fontWeight: 600 }}>Nome</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Tipo Balanço</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Análise Balanço</TableCell>
                                        <TableCell
                                            sx={{ fontSize: '16px', fontWeight: 600 }}>Data Início</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Data Final</TableCell>
                                        <TableCell
                                            sx={{ fontSize: '16px', fontWeight: 600 }}>Tipo Visualização</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Categoria/Título</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Ação</TableCell>
                                        <TableCell sx={{ fontSize: '16px', fontWeight: 600 }}>Dashboard</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {(rowsPerPage > 0
                                            ? filteredData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                            : filteredData
                                    ).map((balanco, index) => (
                                        <TableRow key={balanco.id}>
                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.nome}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.tipoBalanco}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.analise_balanco}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.data_inicio_balanco}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.data_final_balanco}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.tipo_visualizacao}</TableCell>

                                            <TableCell
                                                sx={{ fontSize: '16px' }}>{balanco.categoria_titulo_contabil}</TableCell>

                                            <TableCell key={balanco.id} align={balanco.align}>
                                                <span style={{ display: 'flex' }}>
                                                    <IconButton
                                                        aria-label="Editar"
                                                        onClick={() => handleEdit(balanco.id)}
                                                    >
                                                        <CiEdit />
                                                    </IconButton>
                                                    <IconButton
                                                        aria-label="Apagar"
                                                        onClick={() => handleApagar(balanco.id)}
                                                    >
                                                        <CiTrash />
                                                    </IconButton>
                                                    <IconButton
                                                        aria-label="Visualizar Balanço"
                                                        onClick={() => VisualizarBalanco(balanco.id)}
                                                    >
                                                        <IoEyeOutline />
                                                    </IconButton>
                                                </span>
                                            </TableCell >
                                            <TableCell key={balanco.id} align={balanco.align}>
                                                <input
                                                    type="checkbox"
                                                    checked={balanco.dashboard_check}
                                                    onChange={(event) => handleCheckboxChange(event, balanco.id)}
                                                />
                                            </TableCell>
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

export default HospitalBalanco;