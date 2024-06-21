// src/components/Login.js

import React, { useState } from 'react';
import '../styles/Login.css';
import { loginUser } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { saveCustomerEmailToCookie } from '../utils/cookieUtils';
import { useAuth } from '../context/AuthContext';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleLogin = async () => {
        try {
            const response = await loginUser(email, password);
            setMessage(response.message);
            if (response.status === 'success') {
                saveCustomerEmailToCookie(response.email);
                login(email);
                navigate('/dashboard', { replace: true });
            }
        } catch (error) {
            setMessage(error.message || 'An error occurred');
        }
    };

    return (
        <div className="container">
            <div className="form-wrapper">
                <h1 className="title">Login</h1>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="input"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="input"
                />
                <button onClick={handleLogin} className="button">Login</button>
                {message && <p className="message">{message}</p>}
            </div>
        </div>
    );
};

export default Login;
