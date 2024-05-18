package com.example.demo.models;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.example.demo.repositories.AdminRepository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "products")

public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "product_id")
    private Long productID;
    private String productName;
    private double price;
    private Date releaseDate;
    private Date availabilityDate;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "admin_id", unique = false) // Name of the foreign key column in the product table
    private Admin admin;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    public Product(Long productID, String productName, double price, Date releaseDate, Date availabilityDate, int quantity, Admin admin, List<OrderProduct> orderProducts) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.releaseDate = releaseDate;
        this.availabilityDate = availabilityDate;
        this.quantity = quantity;
        this.admin = admin;
        this.orderProducts = orderProducts;
    }
    
    public Product() {
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }


    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(Date availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    

}
