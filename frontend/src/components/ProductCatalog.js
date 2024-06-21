import React, { useState, useEffect } from 'react';
import '../styles/ProductCatalog.css';
import { getProducts } from '../services/api';
import { useNavigate } from 'react-router-dom';

const ProductCatalog = () => {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const productsData = await getProducts();
                const parsedProducts = parseProducts(productsData);
                setProducts(parsedProducts);
                setLoading(false);
            } catch (error) {
                setError('Error fetching products');
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    const handleCreateProductClick = () => {
        navigate('/create-product');
    };

    const parseProducts = (productsData) => {
        try {
            const productStrings = productsData.split(/\n/).filter(Boolean);
            const parsedProducts = productStrings.map(productStr => JSON.parse(productStr));
            return parsedProducts;
        } catch (error) {
            console.error('Error parsing products data:', error);
            return [];
        }
    };

    return (
        <div className="product-catalog-container">
            <h1>Product Catalog</h1>
            {loading && <p>Loading...</p>}
            {error && <p className="error">{error}</p>}
            <div className="product-list">
                {products.map(product => (
                    <div key={product.productID} className="product-card">
                        <h2>{product.productName}</h2>
                        <div className="product-info">
                            <strong>Price:</strong> ${product.price.toFixed(2)}<br />
                            <strong>Quantity:</strong> {product.quantity}<br />
                            <strong>Release Date:</strong> {new Date(product.releaseDate).toLocaleDateString()}<br />
                            <strong>Availability Date:</strong> {new Date(product.availabilityDate).toLocaleDateString()}
                        </div>
                    </div>
                ))}
            </div>
            <button className="create-product-button" onClick={handleCreateProductClick}>Create New Product</button>
        </div>
    );
};

export default ProductCatalog;
