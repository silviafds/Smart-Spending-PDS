export const verificarSenhas = (senha: string, verificaSenha: string): string | null => {
    if (senha !== verificaSenha) {
        return 'As senhas não coincidem.';
    }
    return null;
};

export const verificarSenhaLogin = (senha: string): boolean => {
    return !(senha === null || senha === "");
};