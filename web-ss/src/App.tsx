import React from 'react';
import { AppRouter } from "./core/rotas";
import {AuthProvider } from "./core/contexto/Auth";

export const App: React.FC = () => {
     return (
         <AuthProvider>
             <AppRouter />
         </AuthProvider>
     );
 }
export default App;