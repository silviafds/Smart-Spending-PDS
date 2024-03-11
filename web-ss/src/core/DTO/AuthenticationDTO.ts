export class AuthenticationDTO {
    private _login: string;
    private _password: string;

    constructor(login: string, password: string) {
        this._login = login;
        this._password = password;
    }


    get login(): string {
        return this._login;
    }

    set login(value: string) {
        this._login = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }
}