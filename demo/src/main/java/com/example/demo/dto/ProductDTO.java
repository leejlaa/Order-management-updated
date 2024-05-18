package com.example.demo.dto;

import java.util.Date;


public class ProductDTO {
    private Long productID;
    private String productName;
    private double price;
    private int quantity;
    private Date releaseDate;
    private Date availabilityDate;

    public ProductDTO(Long ID, String productName, double price, int quantity, Date releaseDate, Date availabilityDate) {
        this.productID = ID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.releaseDate = releaseDate;
        this.availabilityDate = availabilityDate;
    }


    public ProductDTO() {
    }


    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long ID) {
        this.productID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

}