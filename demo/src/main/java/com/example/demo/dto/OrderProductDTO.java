package com.example.demo.dto;

public class OrderProductDTO {

    private String productName;
    private int quantity;

    public OrderProductDTO() {
    }

    public OrderProductDTO(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    // Getters and Setters

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional: Override toString() for debugging purposes
    @Override
    public String toString() {
        return "OrderProductDTO{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
