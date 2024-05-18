package com.example.demo.models;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;


@Entity
@Table(name = "admins")
@AttributeOverride(name = "ID", column = @Column(name = "admin_id"))

public class Admin  extends User{

    @ManyToOne
    @JoinColumn(name = "created_by_admin_id")
    private Admin createdByAdmin;

    @OneToMany(mappedBy = "createdByAdmin")
    private List<Admin> createdAdmins;

    @OneToMany(mappedBy = "admin")
    private List<Customer> createdCustomers;

    public Admin getCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(Admin createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public List<Admin> getCreatedAdmins() {
        return createdAdmins;
    }

    public void setCreatedAdmins(List<Admin> createdAdmins) {
        this.createdAdmins = createdAdmins;
    }

    public List<Customer> getCreatedCustomers() {
        return createdCustomers;
    }

    public void setCreatedCustomers(List<Customer> customers) {
        this.createdCustomers = customers;
    }

    public Admin(Long ID, String username, String password, String email, Admin createdByAdmin, List<Admin> createdAdmins, List<Customer> customers) {
        super(ID,username, password, email, "admin");
        this.createdByAdmin = createdByAdmin;
        this.createdAdmins = createdAdmins;
        this.createdCustomers = customers;
    }

    public Admin() {
        super();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Admin other = (Admin) obj;
        return Objects.equals(getID(), other.getID()) &&
                Objects.equals(getUserName(), other.getUserName()) &&
                Objects.equals(getPassword(), other.getPassword()) &&
                Objects.equals(getEmail(), other.getEmail()) &&
                Objects.equals(getCreatedByAdmin(), other.getCreatedByAdmin()) &&
                Objects.equals(getCreatedAdmins(), other.getCreatedAdmins()) &&
                Objects.equals(getCreatedCustomers(), other.getCreatedCustomers());
    }
   
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", this.getID());
        json.put("username", this.getUserName());
        json.put("password", this.getPassword());
        json.put("email", this.getEmail());
        json.put("role", this.getRole());
        json.put("createdByAdmin", this.createdByAdmin != null ? createdByAdmin.toJson() : null);
        json.put("createdAdmins", this.createdAdmins);
        json.put("createdCustomers", this.createdCustomers);
        

        return json.toString();
    }
}
