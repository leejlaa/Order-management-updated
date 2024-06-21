import React from 'react';
import { BrowserRouter as Router, Route,Routes  } from 'react-router-dom';
import Home from './components/Home';
import OrderList from './components/OrderList';
import CustomerList from './components/CustomerList';
import PlaceOrderForm from './components/PlaceOrderForm';
import CustomerForm from './components/CustomerForm';
import Navbar from './components/Navbar';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import AdminForm from './components/AdminForm';
import { AuthProvider } from './context/AuthContext';
import './styles/App.css';
import ProductCatalog from './components/ProductCatalog';
import ProductForm from './components/ProductForm.js'

function App() {
  return (
      <AuthProvider>
          <Router>
              <Navbar />
              <div className="container">
                  <h1>Order Management System</h1>
                  <Routes>
                      <Route path="/" element={<Home />} />
                      <Route path="/login" element={<Login />} />
                      <Route path="/dashboard" element={<Dashboard />} />
                      <Route path="/orders" element={<OrderList />} />
                      <Route path="/orders/new" element={<PlaceOrderForm />} />
                      <Route path="/customers/new" element={<CustomerForm />} />
                      <Route path="/admins/new" element={<AdminForm />} />
                     <Route path="/products" element={<ProductCatalog />} />
                    <Route path="/create-product" element={<ProductForm/>} />
                  </Routes>
              </div>
          </Router>
      </AuthProvider>
  );
}


export default App;
