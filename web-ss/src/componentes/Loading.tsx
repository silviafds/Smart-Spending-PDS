import React from 'react';

const Loading: React.FC = () => {
    return (
        <div className="flex flex-col justify-center items-center h-screen">
            <div className="animate-spin rounded-full h-24 w-24 border-b-2 border-gray-900"></div>
            <h2 className="text-3xl	mt-4">Buscando dados...</h2>
        </div>
    );
};

export default Loading;