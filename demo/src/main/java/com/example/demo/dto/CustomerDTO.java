package com.example.demo.dto;

import java.util.Date;

import com.example.demo.models.Address;

public class CustomerDTO {
    
    private Long ID;
    private String userName;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private AddressDTO currentResidence;
    
    public CustomerDTO(Long ID, String userName, String email, String role, String firstName, String lastName, Date dateOfBirth, AddressDTO currentResidence) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.currentResidence = currentResidence;
    }

    public CustomerDTO() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }   

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressDTO getCurrentResidence() {
        return currentResidence;
    }

    public void setCurrentResidence(AddressDTO currentResidence) {
        this.currentResidence = currentResidence;
    }



}
