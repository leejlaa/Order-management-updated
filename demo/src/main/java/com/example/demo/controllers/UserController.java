package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.models.Admin;
import com.example.demo.models.Customer;
import com.example.demo.services.impl.AdminServiceImpl;
import com.example.demo.services.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired 
    private AdminServiceImpl adminService;

    @Autowired
    private CustomerServiceImpl customerService;
    

    @GetMapping
    public String getUsers() {
        ObjectMapper objectMapper = new ObjectMapper();

        String users = "";

        List<Admin> admins = adminService.getAllAdmins();
        List<Customer> customers = customerService.getCustomers();

        for (Admin admin : admins) {
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setUserName(admin.getUserName());
            adminDTO.setID(admin.getID());
            adminDTO.setEmail(admin.getEmail());
            adminDTO.setRole(admin.getRole());
            String json;
            try {
                json = objectMapper.writeValueAsString(adminDTO);
                users += json+"\n";
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setUserName(customer.getUserName());
            customerDTO.setID(customer.getID());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setRole(customer.getRole());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setDateOfBirth(customer.getDateOfBirth());

            AddressDTO address = new AddressDTO();
            
            address.setStreetAddress(customer.getCurrentResidence().getStreetAddress());
            address.setCity(customer.getCurrentResidence().getCity());
            address.setCountry(customer.getCurrentResidence().getCountry());
            address.setPostalCode(customer.getCurrentResidence().getPostalCode());
    
            customerDTO.setCurrentResidence(address);

            String json = "";
            try {
                json = objectMapper.writeValueAsString(customerDTO);
                users += json+"\n";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        
        return users;
    }

    
}
