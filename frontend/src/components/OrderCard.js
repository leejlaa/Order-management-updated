// src/components/OrderCard.js

import React from 'react';
import '../styles/OrderCard.css';

const OrderCard = ({ order }) => {
    return (
        <div className="order-card">
            <h2>Order ID: {order.orderId}</h2>
            {order.orderProducts.map((op, index) => (
                <div key={index} className="order-product">
                    <div className="product-info">
                        <strong>Product Name:</strong> {op.productName}<br />
                        <strong>Quantity:</strong> {op.quantity}<br />
                        <strong>Price:</strong> ${op.price.toFixed(2)}
                    </div>
                </div>
            ))}
            <div className="order-total">
                <strong>Total:</strong> ${order.totalPrice.toFixed(2)}
            </div>
        </div>
    );
};

export default OrderCard;
