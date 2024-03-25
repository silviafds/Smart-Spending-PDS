import { useState } from 'react';
import { BsArrowLeftCircle } from "react-icons/bs";
import { IoWalletOutline } from "react-icons/io5";
import { LuLayoutDashboard } from "react-icons/lu";
import { IoBarChartOutline } from "react-icons/io5";
import { GrConfigure } from "react-icons/gr";
import { TbPigMoney } from "react-icons/tb";
import { GiPayMoney, GiReceiveMoney } from "react-icons/gi";
import {MdAttachMoney, MdOutlineAccountTree, MdOutlineSubtitles} from "react-icons/md";
import { BsBank } from "react-icons/bs";
import { ChevronDownIcon } from '@heroicons/react/20/solid';

export function Sidebar() {
    const [open, setOpen] = useState(true);
    const [submenuOpen, setSubmenuOpen] = useState(false);

    const Menus = [
        {
            id: 0,
            title: "Lançamento",
            icon: <IoWalletOutline />,
            submenu: true,
            submenuItems: [
                { id: 1, title: "Despesa", icon: <GiPayMoney /> },
                { id: 2, title: "Receita", icon: <GiReceiveMoney /> },
            ],
        },
        { id: 5, title: "Projetos", icon: <TbPigMoney /> },
        { id: 4, title: "Saldo", icon: <MdAttachMoney /> },
        { id: 6, title: "Balanço", spacing: true, icon: <IoBarChartOutline /> },
        { id: 7, title: "Dashboard", icon: <LuLayoutDashboard /> },
        { id: 8, title: "Configurações", spacing: true, icon: <GrConfigure /> },
        /*{ id: 9, title: "Conta Interna", icon: <MdOutlineAccountTree /> },
        { id: 10, title: "Conta Bancária", icon: <BsBank /> },
        { id: 11, title: "Categorização", icon: <MdOutlineSubtitles /> },*/
    ];

    const handleMenuClick = ({ title }: { title: any }) => {
        switch (title) {
            case 'Receita':
                window.location.href = "/receita/";
                break;
            case 'Despesa':
                window.location.href = "/despesa/";
                break;
            case 'Projetos':
                window.location.href = "/projetos/";
                break;
            case 'Balanço':
                window.location.href = "/balanco/";
                break;
            case 'Dashboard':
                window.location.href = "/dashboard/";
                break;
            case 'Conta Interna':
                window.location.href = "/contaInterna/";
                break;
            case 'Conta Bancária':
                window.location.href = "/contaBancaria/";
                break;
            case 'Configurações':
                window.location.href = "/telaConfiguracao/";
                break;
            default:
                break;
        }
    };

    return (
        <div className="flex">
            <aside className={`border-r border-gray-200 border-solid border-[1px] h-screen p-4 pt-6 ${open ? "w-60" : "w-20"} duration-300 relative h-screen sticky top-0`}>
                <BsArrowLeftCircle className={`bg-white text-sky-950 text-3xl rounded-full absolute -right-3 cursor-pointer ${!open && "rotate-180"}`} onClick={() => setOpen(!open)} />

                <div className="inline-flex">
                    <ul className="pt-3">
                        {Menus.map((menu) => (
                            <div key={menu.id}>
                                <li className={"text-lg text-zinc-800 text-sm flex items-center" + `gap-x-4 cursor-pointer p-3 hover:bg-sky-200 rounded-md ${menu.spacing ? "mt-9" : "mt-2"}`} onClick={() => handleMenuClick({ title: menu.title })}>
                                    <span className="text-2xl block float-left">
                                        {menu.icon ? menu.icon : <LuLayoutDashboard />}
                                    </span>
                                    <span className={`text-lg font-medium flex-1 ml-2 duration-200 ${!open && "hidden"}`}>
                                        {menu.title}
                                    </span>
                                    {menu.submenu && open && (
                                        <ChevronDownIcon className={`h-6 w-6 ${submenuOpen && "rotate-180"}`} onClick={() => setSubmenuOpen(!submenuOpen)} />
                                    )}
                                </li>
                                {menu.submenu && submenuOpen && open && (
                                    <ul>
                                        {menu.submenuItems.map((submenuItem) => (
                                            <li key={submenuItem.title} className={"text-zinc-800 text-lg flex items-center gap-x-4 cursor-pointer ml-10 p-2 px-5 hover:bg-sky-200 rounded-md"} onClick={() => handleMenuClick({ title: submenuItem.title })}>
                                                <span>
                                                    {submenuItem.title}
                                                </span>
                                                <span className="text-2xl block float-left ml-[1rem]">
                                                    {submenuItem.icon ? submenuItem.icon : <LuLayoutDashboard />}
                                                </span>
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </div>
                        ))}
                    </ul>
                </div>
            </aside>
        </div>
    )
}
