import React from 'react';
import { AppRouter } from "./rotas";
import {AuthProvider } from "../src/contexto/Auth";

export const App: React.FC = () => {
     return (
         <AuthProvider>
             <AppRouter />
         </AuthProvider>
     );
 }
export default App;