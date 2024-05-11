export class ContaInterna {
    private id: number;
    private nome: string;
    private desabilitarConta: boolean;

    constructor(id: number, nome: string, desabilitarConta: boolean) {
        this.id = id;
        this.nome = nome;
        this.desabilitarConta = desabilitarConta;
    }

    getId(): number {
        return this.id;
    }

    setId(id: number): void {
        this.id = id;
    }

    getNome(): string {
        return this.nome;
    }

    setNome(nome: string): void {
        this.nome = nome;
    }

    getDesabilitarConta(): boolean {
        return this.desabilitarConta;
    }

    setDesabilitarConta(desabilitarConta: boolean): void {
        this.desabilitarConta = desabilitarConta;
    }
}
