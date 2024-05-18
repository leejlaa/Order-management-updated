package com.example.demo.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@AttributeOverride(name = "ID", column = @Column(name = "customer_id"))

public class Customer extends User{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address currentResidence;

   
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Customer(Long ID, String username, String password, String email, String firstName, String lastName, Date dateOfBirth, Address currentResidence, List<Order> orders, Admin admin) {
        super (ID, username, password, email, "customer");
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.currentResidence = currentResidence;
        this.orders = orders;
        this.admin = admin;
    }
   
    public Customer() {
        super();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
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

    public Address getCurrentResidence() {
        return currentResidence;
    }

    public void setCurrentResidence(Address currentResidence) {
        this.currentResidence = currentResidence;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
    
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Customer customer = (Customer) o;
    return Objects.equals(firstName, customer.firstName) &&
            Objects.equals(lastName, customer.lastName) &&
            Objects.equals(dateOfBirth, customer.dateOfBirth) &&
            Objects.equals(currentResidence, customer.currentResidence) &&
            Objects.equals(orders, customer.orders) &&
            Objects.equals(admin, customer.admin);
}


// public String toJson(){

// }

}

    


