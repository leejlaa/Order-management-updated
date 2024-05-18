package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AdminDTO;
import com.example.demo.models.Admin;
import com.example.demo.models.Product;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.AdminService;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ProductRepository productRepository;


    public Admin createAdmin(Admin admin) {
       
        Admin existingAdmin = adminRepository.findByUserName(admin.getUserName());
        if (existingAdmin != null) {
            return null;
        }
        return adminRepository.save(admin);
    }

    public Admin getAdmin(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public boolean exists(String userName){
        if(adminRepository.findByUserName(userName) != null){
            return true;
        }
        return false;
    }


    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public List<AdminDTO> getAdmins() {
        List<Admin> admins =  adminRepository.findAll();
        List<AdminDTO> adminDTOs = new ArrayList<>(admins.size());
       
        for (int i = 0; i < admins.size(); i++) {
            AdminDTO adminDTO = new AdminDTO();
            Admin admin = admins.get(i);
            
            // Set AdminDTO properties from Admin
            adminDTO.setID(admin.getID());
            adminDTO.setUserName(admin.getUserName());
            adminDTO.setEmail(admin.getEmail());
            adminDTO.setRole(admin.getRole());
            adminDTOs.add(adminDTO);
        }

    return adminDTOs;
    }

    public Admin updateAdmin(Long id, Admin admin) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setUserName(admin.getUserName());
            existingAdmin.setPassword(admin.getPassword());
            return adminRepository.save(existingAdmin);
        }
        return null;
    }

    public boolean deleteAdmin(String userName) {
        Admin existingAdmin = adminRepository.findByUserName(userName);
        if (existingAdmin != null) {
            adminRepository.delete(existingAdmin);
            return true;
        }
        return false;
    }

    public boolean validateAdmin(String userName, String password) {
       Admin admin = adminRepository.findByUserNameAndPassword(userName, password);
        return admin != null;
    }

    public Admin getAdminByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    public String JSONAdmins(){   
        return adminRepository.findAllAdminsJson();
    }
    

        @Transactional
        public Admin updateAdmin(Admin admin) {

            Admin updatedAdmin = adminRepository.save(admin);

            // Update related products
            List<Product> products = productRepository.findByAdmin(admin);
            for (Product product : products) {
                product.setAdmin(updatedAdmin);
                productRepository.save(product);
            }

            return updatedAdmin;
        }

        public String exportAdmins(){

            List<Admin> admins = adminRepository.findAll();
            String json = "{ \"admins\": [";
            for (Admin admin : admins) {
                json += admin.toJson() + ",";
            }
            if (json.endsWith(",")) {
                json = json.substring(0, json.length() - 1);
            }
            json += "]}";
        return json;
        }
}
