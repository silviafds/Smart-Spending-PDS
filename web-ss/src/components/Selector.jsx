import React, { useState, useEffect } from "react";
import { BiChevronDown } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";

const Selector = ({ dado, placeholder, valorSelecionado, onGenericoSelect = (dado) => {} }) => {
    const [inputValue, setInputValue] = useState("");
    const [selected, setSelected] = useState(valorSelecionado);
    const [open, setOpen] = useState(false);

    const filteredDados = Array.isArray(dado)
        ? [ { id: "", nome: "" }, ...dado.filter(dados =>
            dados.nome.toLowerCase().includes(inputValue.toLowerCase())
        )]
        : [];

    useEffect(() => {
        setSelected(valorSelecionado);
    }, [valorSelecionado]);

    const handleSelect = (dado) => {
        setSelected(dado.nome);
        setInputValue("");
        onGenericoSelect(dado);
        setOpen(false); // Fechar o seletor após a seleção de um item
    };

    return (
        <div className="w-86 text-lg">
            <div
                onClick={() => setOpen(!open)}
                className={`bg-white w-full p-2 flex items-center justify-between rounded ${
                    !selected && "text-gray-700"
                }`}
            >
                {selected
                    ? selected.length > 25
                        ? selected.substring(0, 25) + "..."
                        : selected
                    : placeholder}
                <BiChevronDown size={20} className={`${open && "rotate-180"}`} />
            </div>
            <ul
                className={`bg-gray-100 mt-2 overflow-y-auto ${
                    open ? "max-h-60" : "max-h-0"
                } `}
            >
                <div className="flex items-center px-2 sticky top-0 bg-white">
                    <AiOutlineSearch size={18} className="text-gray-700" />
                    <input
                        type="text"
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                        placeholder="Ou pesquise"
                        className="placeholder:text-gray-700 p-2 outline-none"
                    />
                </div>
                {filteredDados && filteredDados.map(dado => (
                    <li
                        key={dado.id}
                        className={`p-2 text-2xl text-lg hover:bg-sky-600 hover:text-white
                        ${dado.nome && selected && dado.nome.toLowerCase() === selected.toLowerCase() && "bg-sky-600 text-white"}`}
                        onClick={() => handleSelect(dado)}
                    >
                        {dado.nome}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Selector;