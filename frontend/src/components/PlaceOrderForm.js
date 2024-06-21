import React, { useState } from 'react';
import { placeOrder } from '../services/api';
import '../styles/OrderForm.css';

const PlaceOrderForm = () => {
    const [customerId, setCustomerId] = useState('');
    const [orderProducts, setOrderProducts] = useState([{ productName: '', quantity: '' }]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const orderRequest = {
            customerId: parseInt(customerId),
            orderProducts: orderProducts
        };

        try {
            const response = await placeOrder(customerId, orderRequest);
            console.log('Order placed successfully:', response);
            // Handle success: redirect, show success message, etc.
        } catch (error) {
            console.error('Error placing order:', error);
            // Handle error: display error message, retry logic, etc.
        }
    };

    const handleProductChange = (index, event) => {
        const { name, value } = event.target;
        const updatedProducts = [...orderProducts];
        updatedProducts[index] = { ...updatedProducts[index], [name]: value };
        setOrderProducts(updatedProducts);
    };

    const handleAddProduct = () => {
        setOrderProducts([...orderProducts, { productName: '', quantity: '' }]);
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="text"
                placeholder="Customer ID"
                value={customerId}
                onChange={(e) => setCustomerId(e.target.value)}
            />
            <br />
            {orderProducts.map((product, index) => (
                <div key={index}>
                    <input
                        type="text"
                        placeholder="Product Name"
                        name="productName"
                        value={product.productName}
                        onChange={(e) => handleProductChange(index, e)}
                    />
                    <input
                        type="text"
                        placeholder="Quantity"
                        name="quantity"
                        value={product.quantity}
                        onChange={(e) => handleProductChange(index, e)}
                    />
                </div>
            ))}
            <button type="button" onClick={handleAddProduct}>Add Product</button>
            <br />
            <button type="submit">Place Order</button>
        </form>
    );
};

export default PlaceOrderForm;