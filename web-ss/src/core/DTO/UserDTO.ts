import { Role } from '../ENUM/Role';

export class UserDTO {
    private _nome: string;
    private _sobrenome: string;
    private _login: string;
    private _datanascimento: Date;
    private _email: string;
    private _password: string;
    private _role: Role;
    constructor(nome: string, sobrenome: string, login: string, datanascimento: Date, email: string, password: string,
                role: Role) {
        this._nome = nome;
        this._sobrenome = sobrenome;
        this._login = login;
        this._datanascimento = datanascimento;
        this._email = email;
        this._password = password;
        this._role = role;
    }

    get nome(): string {
        return this._nome;
    }

    set nome(value: string) {
        this._nome = value;
    }

    get sobrenome(): string {
        return this._sobrenome;
    }

    set sobrenome(value: string) {
        this._sobrenome = value;
    }

    get login(): string {
        return this._login;
    }

    set login(value: string) {
        this._login = value;
    }

    get datanascimento(): Date {
        return this._datanascimento;
    }

    set datanascimento(value: Date) {
        this._datanascimento = value;
    }

    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get role(): Role {
        return this._role;
    }

    set role(value: Role) {
        this._role = value;
    }
}