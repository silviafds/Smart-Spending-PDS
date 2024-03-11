export class ContaInternaDTO {
    private _id: number | undefined;
    private _nome: string;
    private _desabilitarConta: boolean | undefined;

    constructor(nome: string) {
        this._nome = nome;
    }

    get nome(): string {
        return this._nome;
    }

    set nome(value: string) {
        this._nome = value;
    }

    get id(): number {
        return <number>this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get desabilitarConta(): boolean {
        return <boolean>this._desabilitarConta;
    }

    set desabilitarConta(value: boolean) {
        this._desabilitarConta = value;
    }
}