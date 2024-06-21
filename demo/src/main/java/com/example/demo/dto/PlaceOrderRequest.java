package com.example.demo.dto;

import java.util.List;

public class PlaceOrderRequest {

    private Long customerId;
    private List<OrderProductDTO> orderProducts;


    public PlaceOrderRequest() {
    }

    public PlaceOrderRequest(Long customerId, List<OrderProductDTO> orderProducts) {
        this.customerId = customerId;
        this.orderProducts = orderProducts;
    }
    // Getters and Setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

}
