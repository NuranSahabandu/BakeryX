package com.example.BakeryX.entity;

import java.time.LocalDate;

public class CustomOrder {
    private Long id;
    private String flavor;
    private String shape;
    private String message;
    private LocalDate deliveryDate;
    private String status;


    public CustomOrder() {
    }

    public CustomOrder(Long id, String flavor, String shape, String message, LocalDate deliveryDate, String status) {
        this.id = id;
        this.flavor = flavor;
        this.shape = shape;
        this.message = message;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


