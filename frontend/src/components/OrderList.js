// src/components/OrderList.js

import React, { useState, useEffect } from 'react';
import '../styles/OrderList.css';
import { getOrdersByCustomerEmail } from '../services/api';
import { getCustomerEmailFromCookie } from '../utils/cookieUtils';
import OrderCard from './OrderCard';

const OrderList = () => {
    const [orders, setOrders] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const customerEmail = getCustomerEmailFromCookie();

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const ordersData = await getOrdersByCustomerEmail(customerEmail);
                const parsedOrders = parseOrders(ordersData);
                setOrders(parsedOrders);
                setLoading(false);
            } catch (error) {
                setError('Error fetching orders');
                setLoading(false);
            }
        };

        if (customerEmail) {
            fetchOrders();
        }
    }, [customerEmail]);

    const parseOrders = (ordersData) => {
        const orderStrings = ordersData.split(/(?=Order ID: \d+)/).filter(Boolean);

        const parsedOrders = orderStrings.map(orderStr => {
            const orderIdMatch = orderStr.match(/Order ID: (\d+)/);
            const orderId = orderIdMatch ? orderIdMatch[1] : null;

            const productMatches = orderStr.match(/{[^{}]+}/g) || [];
            const orderProducts = productMatches.map(product => {
                const productInfo = JSON.parse(product);
                return {
                    productName: productInfo.productName,
                    quantity: productInfo.quantity,
                    price: productInfo.price
                };
            });

            const totalPriceMatch = orderStr.match(/Total: (\d+\.\d+)/);
            const totalPrice = totalPriceMatch ? parseFloat(totalPriceMatch[1]) : 0;

            return {
                orderId: orderId,
                orderProducts: orderProducts,
                totalPrice: totalPrice
            };
        });

        return parsedOrders;
    };

    return (
        <div className="order-list-container">
            <h1>Customer Orders</h1>
            {loading && <p>Loading...</p>}
            {error && <p className="error">{error}</p>}
            <div className="order-list">
                {orders.map(order => (
                    <OrderCard key={order.orderId} order={order} />
                ))}
            </div>
        </div>
    );
};

export default OrderList;
