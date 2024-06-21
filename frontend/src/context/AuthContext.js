// src/context/AuthContext.js

import React, { createContext, useContext, useState, useEffect } from 'react';
import Cookies from 'js-cookie';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [email, setEmail] = useState(null);

    useEffect(() => {
        const userEmail = Cookies.get('email');
        if (userEmail) {
            setEmail(userEmail);
        }
    }, []);

    const login = (userEmail) => {
        setEmail(userEmail);
        Cookies.set('email', userEmail);
    };

    const logout = () => {
        setEmail(null);
        Cookies.remove('email');
    };

    return (
        <AuthContext.Provider value={{ email, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
