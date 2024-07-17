"use client";

import React, { createContext, useContext, useState, useEffect } from 'react';

interface AuthContextType {
    token: string | null;
    login: (jwtToken: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
        // Check if there's a token in localStorage when the app loads
        const storedToken = localStorage.getItem('jwtToken');
        if (storedToken) {
            setToken(storedToken);
        }
    }, []);

    const login = (jwtToken: string) => {
        setToken(jwtToken);
        localStorage.setItem('jwtToken', jwtToken);
    };

    const logout = () => {
        setToken(null);
        localStorage.removeItem('jwtToken');
    };

    const value = {
        token,
        login,
        logout,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export default AuthProvider;