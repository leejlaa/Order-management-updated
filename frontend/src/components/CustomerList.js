import React, { useEffect, useState } from 'react';
import { getCustomers } from '../services/api';

const CustomerList = () => {
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const data = await getCustomers();
                setCustomers(data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchCustomers();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error loading customers: {error.message}</p>;

    return (
        <div>
            <h1>Customer List</h1>
            <ul>
                {customers.map(customer => (
                    <li key={customer.id}>
                        {customer.userName} ({customer.email})
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default CustomerList;
