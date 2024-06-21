package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.models.Admin;
import com.example.demo.models.Customer;
import com.example.demo.models.User;
import com.example.demo.services.impl.AdminServiceImpl;
import com.example.demo.services.impl.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired 
    private AdminServiceImpl adminService;

    @Autowired
    private CustomerServiceImpl customerService;
    



    @GetMapping("/{email}")
    public String getRole(@PathVariable String email) {
        Long adminId = adminService.getAdminByEmail(email);
        if (adminId != null && adminId > 0) {
            return "admin";
        } else if (customerService.getCustomer(email) != null) {
            return "customer";
        } else {
            return "not found";
        }
    }
    
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
            String json = "";
            try {
                json = objectMapper.writeValueAsString(adminDTO);
                users += json+"\n";
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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

             
            try {
                json = objectMapper.writeValueAsString(customerDTO);
                users += json+"\n";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        
          
        }
        return users;}

       @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        List<Admin> admins = adminService.getAllAdmins();
        List<Customer> customers = customerService.getCustomers();

        for (Admin admin : admins) {
            if (admin.getEmail().equals(loginRequest.getEmail()) && admin.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(new LoginResponse("success", "Admin authenticated successfully", admin.getEmail()));
            }
        }

        for (Customer customer : customers) {
            if (customer.getEmail().equals(loginRequest.getEmail()) && customer.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(new LoginResponse("success", "Customer authenticated successfully", customer.getEmail()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("error", "Invalid credentials",null ));
    }

}
class LoginRequest {
    private String email;
    private String password;

    // Constructor
    public LoginRequest() {}

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


class LoginResponse {
    private String status;
    private String message;
    private String email;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String status, String message, String email) {
        this.status = status;
        this.message = message;
        this.email =  email;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


    

