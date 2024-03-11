import React from 'react';
import { Route, Navigate } from 'react-router-dom';

interface PrivateRouteProps {
    path: string;
    element: React.ReactNode;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ path, element }) => {
    return <Route path={path} element={element} />;
};

export default PrivateRoute;
