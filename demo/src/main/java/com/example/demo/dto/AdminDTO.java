package com.example.demo.dto;

import jakarta.persistence.Column;

public class AdminDTO {
    private Long ID;
    private String userName;
    private String email;
    private String role;


    public AdminDTO(Long ID, String userName, String email, String role) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public AdminDTO() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    
}
