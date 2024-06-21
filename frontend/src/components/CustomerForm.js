import React, { useState, useEffect } from 'react';
import { createCustomer, getAdminByEmail } from '../services/api';
import '../styles/CustomerForm.css';
import Cookies from 'js-cookie';

const CustomerForm = () => {
    const [customer, setCustomer] = useState({
        userName: '',
        password: '',
        email: '',
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        role: 'customer', // Set the role to 'customer' by default
        currentResidence: {
            streetAddress: '',
            city: '',
            country: '',
            postalCode: ''
        }
    });
    const [adminId, setAdminId] = useState(1);

    useEffect(() => {
        const fetchAdminId = async () => {
            const email = Cookies.get('email');
            if (email) {
                try {
                    const admin = await getAdminByEmail(email);
                    console.log(admin);
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

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCustomer(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleAddressChange = (e) => {
        const { name, value } = e.target;
        setCustomer(prevState => ({
            ...prevState,
            currentResidence: {
                ...prevState.currentResidence,
                [name]: value
            }
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {

            await createCustomer(adminId, customer);
            alert('Customer created successfully');
        } catch (error) {
            console.error('Error creating customer:', error);
            alert('Failed to create customer');
        }
    };

    return (
        <div className="customer-form-container">
            <h1>Create Customer</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="userName"
                    placeholder="Username"
                    value={customer.userName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={customer.password}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={customer.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="firstName"
                    placeholder="First Name"
                    value={customer.firstName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={customer.lastName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="date"
                    name="dateOfBirth"
                    placeholder="Date of Birth"
                    value={customer.dateOfBirth}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="streetAddress"
                    placeholder="Street Address"
                    value={customer.currentResidence.streetAddress}
                    onChange={handleAddressChange}
                    required
                />
                <input
                    type="text"
                    name="city"
                    placeholder="City"
                    value={customer.currentResidence.city}
                    onChange={handleAddressChange}
                    required
                />
                <input
                    type="text"
                    name="country"
                    placeholder="Country"
                    value={customer.currentResidence.country}
                    onChange={handleAddressChange}
                    required
                />
                <input
                    type="text"
                    name="postalCode"
                    placeholder="Postal Code"
                    value={customer.currentResidence.postalCode}
                    onChange={handleAddressChange}
                    required
                />
                <button type="submit">Create Customer</button>
            </form>
        </div>
    );
};

export default CustomerForm;
