package com.example.demo.services.impl;

import java.util.List;


import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Address;
import com.example.demo.models.Customer;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository   addressRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Customer createCustomer(Long adminID, Customer customer) {
        // Check if the address already exists in the database
        java.util.Optional<Address> existingAddress = addressRepository.findByStreetAddress(customer.getCurrentResidence().getStreetAddress());

        // If the address exists, update the customer's current residence to the existing address
        existingAddress.ifPresent(customer::setCurrentResidence);
        if(existingAddress.isEmpty()){
            addressRepository.save(customer.getCurrentResidence());
        }
        

        customer.setAdmin(adminRepository.findByID(adminID));
        // Save the customer (with potentially updated current residence) to the database
        return customerRepository.save(customer);
    }

    public Customer getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setCurrentResidence(customer.getCurrentResidence());
            existingCustomer.setDateOfBirth(customer.getDateOfBirth());
            existingCustomer.setOrders(customer.getOrders());
            existingCustomer.setEmail(customer.getEmail());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    public boolean deleteCustomer(String userName) {
        Customer existingCustomer = customerRepository.findByUserName(userName);
        if (existingCustomer != null) {
            customerRepository.delete(existingCustomer);
            return true;
        }
        return false;
    }

     public String JSONCustomers() {
        // List<Customer> customers = customerRepository.findAllCustomersWithOrdersAndProducts();
        // ObjectMapper objectMapper = new ObjectMapper();
        // try {
        //     return objectMapper.writeValueAsString(customers);
        // } catch (JsonProcessingException e) {
        //     e.printStackTrace();
        //     return "Error converting to JSON";
        // }
        return customerRepository.findAllCustomersAsJson();
    }

    public Customer getCustomerByUserName(String userName) {
        return customerRepository.findByUserName(userName);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public boolean validateCustomer(String username, String password) {
        Customer customer = customerRepository.findByUserNameAndPassword(username, password);
        return customer != null;
    }

    public boolean customerExists(String userName){
        if(customerRepository.findByUserName(userName) != null){
            return true;
        }
        return false;
    }

    @Override
    public Long getCustomerId(String email) {
        return customerRepository.findCustomerIdByEmail(email);
    }

    
}
