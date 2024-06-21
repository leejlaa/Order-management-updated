// src/services/api.js

import axios from 'axios';

export const getUsers = async () => {
    try {
        const response = await axios.get('/users');
        return response.data;
    } catch (error) {
        console.error("There was an error fetching the users!", error);
        throw error;
    }
};

export const loginUser = async (email, password) => {
    try {
        const response = await axios.post('users/login', { email, password });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const placeOrder = async (customerId, orderRequest) => {
    try {
        const response = await axios.post(`/orders/${customerId}`, orderRequest);
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

export const createCustomer = async (adminId, customer) => {
    try {
        const response = await axios.post(`/customers/${adminId}`, customer, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getCustomers = async () => {
    try {
        const response = await axios.get('/customers');
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getOrdersByCustomerEmail = async (customerEmail) => {
    try {
        const response = await axios.get(`/orders/${customerEmail}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching orders:', error);
        throw error;
    }
};

export const getAdminByEmail = async (email) => {
    const response = await axios.get(`/admins/${email}`);
    return response.data;
};

export const createAdmin = async (superId, adminData) => {
    try {
        const response = await axios.post(`/admins/${superId}`, adminData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const createProduct = async (adminId, product) => {
    try {
        const response = await axios.post(`/products/${adminId}`, product);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getProducts = async () => {
    try {
        const response = await axios.get('/products');
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getCustomerByEmail = async (email) => {    
    try {
        const response = await axios.get(`/customers/${email}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getUserRoleByEmail = async (email) => {
    try {
        const response = await axios.get(`/users/${email}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};
