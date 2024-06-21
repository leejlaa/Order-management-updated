package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Customer;

public interface CustomerService {

    public Customer createCustomer(Long adminID,Customer customer);
    public Long getCustomerId(String email);
    public Customer getCustomer(String email);
    public Customer updateCustomer(Long id, Customer customer);
    public boolean deleteCustomer(String userName);
    public Customer getCustomerByUserName(String userName);
    public List<Customer> getCustomers();
    public boolean validateCustomer(String username, String password);
    public boolean customerExists(String userName);
}
