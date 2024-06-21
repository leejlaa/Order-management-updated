// src/components/AdminForm.js
import React, { useState , useEffect} from 'react';
import '../styles/AdminForm.css';
import { createAdmin, getAdminByEmail } from '../services/api';
import Cookies from 'js-cookie';

const AdminForm = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [adminId, setAdminId] = useState(1);


    useEffect(() => {
        const fetchAdminId = async () => {
            const email = Cookies.get('email');
            if (email) {
                try {
                    const admin = await getAdminByEmail(email);
                    if (admin) {
                        setAdminId(admin); 
                    } else {
                        console.error('Admin data not found');
                    }
                } catch (error) {
                    console.error('Error fetching admin ID:', error);
                }
            } else {
                console.error('Email not found in cookies');
            }
        };
    
        fetchAdminId();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const adminData = {
            userName: username,
            email: email,
            password: password,
            role : 'admin'
        };

        try {
            // Replace with your API call to create an admin
            await createAdmin(adminId, adminData);
            setMessage('Admin created successfully!');
        } catch (error) {
            setMessage('Error creating admin: ' + error.message);
        }
    };

    return (
        <div className="admin-form-container">
            <h1>Create Admin</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Create Admin</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default AdminForm;
