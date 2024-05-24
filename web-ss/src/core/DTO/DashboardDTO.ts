export class DashboardDTO {
    private _identicador_balanco: number | undefined;
    private _isDashboard: boolean | undefined;

    constructor() {
    }

    get identicador_balanco(): number {
        return <number>this._identicador_balanco;
    }

    set identicador_balanco(value: number) {
        this._identicador_balanco = value;
    }

    get dashboard(): boolean {
        return <boolean>this._isDashboard;
    }

    set isDashboard(value: boolean) {
        this._isDashboard = value;
    }
}