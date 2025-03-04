package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminDTO;
import com.example.demo.models.Admin;
import com.example.demo.services.impl.AdminServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admins")
@CrossOrigin(origins="*")
public class AdminController {

    @Autowired 
    private AdminServiceImpl adminService;

    @PostMapping("{superId}")
    public Admin createAdmin(@PathVariable Long superId, @RequestBody Admin admin) {
        return adminService.createAdmin(superId,admin);
    }
    
    @GetMapping
    public List<AdminDTO> getAdmins() {
       return adminService.getAdmins();
    }

    @GetMapping("/{email}")
        public Long getAdmin(@PathVariable String email) {
            return adminService.getAdminByEmail(email);
    }

    @PutMapping("/{id}")   
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.updateAdmin(id, admin);
    }

   

    
    
    
}
