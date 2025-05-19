package com.example.root.modal;

public class Review {

    private String id;
    private String customerName;
    private String product;
    private String message;

    public Review(String id, String customerName, String product, String message) {
        this.id = id;
        this.customerName = customerName;
        this.product = product;
        this.message = message;
    }

    public Review() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
