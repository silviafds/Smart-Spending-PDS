import { Fragment } from 'react'
import React from 'react';
import { Menu, Transition } from '@headlessui/react'
import { ChevronDownIcon } from '@heroicons/react/20/solid'
import { IoLogOutOutline } from 'react-icons/io5';
import { GrConfigure } from "react-icons/gr";
import { IoPersonOutline } from "react-icons/io5";
import axios from 'axios';
import { BACKEND_URL } from "../../core/config/index";

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export function Dropdown({ nomeUsuario }) {
    const token = localStorage.getItem('@Auth:token');
    const axiosConfig = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token ? `Bearer ${token}` : null,
        }
    };

    const handleLogout = async () => {
        await axios.post(BACKEND_URL + '/auth/logout', {}, axiosConfig)
            .then((response) => {
                localStorage.removeItem('@Auth:token');
                localStorage.removeItem('@Auth:user');
                localStorage.removeItem('nomeUser');
                localStorage.clear();
                window.location.href = "/";
            })
            .catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                    window.location.href = "/";
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            });
    };

    return (
        <Menu as="div" className="relative inline-block text-left">
            <div>
                <Menu.Button className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-principal px-3 py-2
                 text-base text-white border-solid border-sky-900 border-[1px] font-semibold shadow-sm hover:bg-sky-900">
                    Olá, {nomeUsuario}
                    <ChevronDownIcon className="-mr-1 h-5 w-5 text-white" aria-hidden="true" />
                </Menu.Button>
            </div>

            <Transition
                as={Fragment}
                enter="transition ease-out duration-100"
                enterFrom="transform opacity-0 scale-95"
                enterTo="transform opacity-100 scale-100"
                leave="transition ease-in duration-75"
                leaveFrom="transform opacity-100 scale-100"
                leaveTo="transform opacity-0 scale-95"
            >
                <Menu.Items className="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <div className="py-1">
                        <Menu.Item className={"text-lg flex justify-between"}>
                            {({ active }) => (
                                <a
                                    href="#"
                                    className={classNames(
                                        active ? 'text-base bg-gray-100 text-gray-900' : 'text-base text-gray-700',
                                        'block px-4 py-2 text-sm'
                                    )}
                                >
                                    Perfil
                                    <IoPersonOutline className="inline-block h-5 w-5 text-black " />
                                </a>
                            )}
                        </Menu.Item>
                        <Menu.Item className={"text-lg flex justify-between"}>
                            {({ active }) => (
                                <a
                                    href="#"
                                    className={classNames(
                                        active ? 'bg-gray-100 text-gray-900' : 'text-gray-700',
                                        'block px-4 py-2 text-sm'
                                    )}
                                >
                                    Configuração
                                    <GrConfigure className="inline-block h-5 w-5 text-black" />
                                </a>
                            )}
                        </Menu.Item>
                        <form method="POST" action="#">
                            <Menu.Item className={"text-lg flex justify-between"}>
                                {({ active }) => (
                                    <button
                                        type="button"
                                        onClick={handleLogout}
                                        className={classNames(
                                            active ? 'bg-gray-100 text-gray-900' : 'text-gray-700',
                                            'block w-full px-4 py-2 text-left text-sm'
                                        )}
                                    >
                                        Sair
                                        <IoLogOutOutline className="inline-block h-5 w-5 text-black " />
                                    </button>
                                )}
                            </Menu.Item>
                        </form>
                    </div>
                </Menu.Items>
            </Transition>
        </Menu>
    )
}