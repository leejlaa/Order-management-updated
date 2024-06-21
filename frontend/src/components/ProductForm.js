import React, { useState, useEffect } from 'react';
import { createProduct, getAdminByEmail } from '../services/api';
import '../styles/ProductForm.css';
import Cookies from 'js-cookie';

const ProductForm = () => {
    const [product, setProduct] = useState({
        productName: '',
        price: '',
        releaseDate: '',
        availabilityDate: '',
        quantity: ''
    });

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [adminId, setAdminId] = useState(1);

    useEffect(() => {
        const fetchAdminId = async () => {
            const email = Cookies.get('email');
            if (email) {
                try {
                    const admin = await getAdminByEmail(email);
                    if (admin) {
                        setAdminId(admin);  // Assuming getAdminByEmail returns an object with an `id` field
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
        setProduct(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const parsedProduct = {
                ...product,
                price: parseFloat(product.price),
                releaseDate: product.releaseDate ? new Date(product.releaseDate) : null,
                availabilityDate: product.availabilityDate ? new Date(product.availabilityDate) : null,
                quantity: parseInt(product.quantity, 10)
            };

            await createProduct(adminId, parsedProduct);
            setSuccess('Product created successfully');
            setError('');
        } catch (error) {
            setError('Error creating product');
            setSuccess('');
        }
    };

    return (
        <div className="product-form-container">
            <h1>Create Product</h1>
            {error && <p className="error">{error}</p>}
            {success && <p className="success">{success}</p>}
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="productName"
                    placeholder="Product Name"
                    value={product.productName}
                    onChange={handleChange}
                    required
                />
                <input
                    type="number"
                    step="0.01"
                    name="price"
                    placeholder="Price"
                    value={product.price}
                    onChange={handleChange}
                    required
                />
                <input
                    type="date"
                    name="releaseDate"
                    placeholder="Release Date"
                    value={product.releaseDate}
                    onChange={handleChange}
                    required
                />
                <input
                    type="date"
                    name="availabilityDate"
                    placeholder="Availability Date"
                    value={product.availabilityDate}
                    onChange={handleChange}
                    required
                />
                <input
                    type="number"
                    name="quantity"
                    placeholder="Quantity"
                    value={product.quantity}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Create Product</button>
            </form>
        </div>
    );
};

export default ProductForm;
