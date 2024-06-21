package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.AdminDTO;
import com.example.demo.models.Admin;

public interface AdminService {

    public Admin createAdmin(Long superId,Admin admin);
    public Admin getAdmin(Long id);
    public Admin getAdminByUserName(String userName);
    public Admin updateAdmin(Long id, Admin admin);
    public boolean deleteAdmin(String userName);
    public boolean exists(String userName);
    public List<AdminDTO> getAdmins();
    public boolean validateAdmin(String userName, String password);
    public Admin updateAdmin(Admin admin);
    public String exportAdmins(); 
    public List<Admin> getAllAdmins();
    public Long getAdminByEmail(String email);
    

}
