// src/components/Navbar.js

import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getUserRoleByEmail } from '../services/api';
import '../styles/Navbar.css';

const Navbar = () => {
    const { email, logout } = useAuth();
    const [userRole, setUserRole] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (email) {
            fetchUserRole(email);
        } else {
            setUserRole(''); // Reset user role when email is null
            setLoading(false); // Stop loading if there's no email
        }
    }, [email]); // Depend on email, so re-fetch role when email changes

    const fetchUserRole = async (email) => {
        try {
            const role = await getUserRoleByEmail(email);
            setUserRole(role);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching user role:', error);
            setLoading(false);
        }
    };

    const handleLogout = () => {
        logout();
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <nav>
            <ul>
                <li>
                    <Link to="/">Home</Link>
                </li>
                {!email && (
                    <li>
                        <Link to="/login">Login</Link>
                    </li>
                )}
                {userRole === 'customer' && (
                    <>
                        {/* <li>
                            <Link to="/orders">Order</Link>
                        </li> */}
                        <li>
                            <Link to="/orders/new">New Order</Link>
                        </li>
                    </>
                )}
                {userRole === 'admin' && (
                    <>
                        <li>
                            <Link to="/orders">Order Management</Link>
                        </li>
                        <li>
                            <Link to="/customers">Customers</Link>
                        </li>
                        <li>
                            <Link to="/products">Product Catalog</Link>
                        </li>
                        <li>
                            <Link to="/customers/new">New Customer</Link>
                        </li>
                        <li>
                            <Link to="/admins/new">New Admin</Link>
                        </li>
                    </>
                )}
                {email && (
                    <li>
                        <button onClick={handleLogout}>Logout</button>
                    </li>
                )}
            </ul>
            {email && <p style={{ color: 'white' }}>Welcome, {email}</p>}
        </nav>
    );
};

export default Navbar;
