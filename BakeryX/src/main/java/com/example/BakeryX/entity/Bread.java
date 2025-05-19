package com.bakery.productmanagement.model;

public class Bread extends Product {

    public Bread() {

    }

    public Bread(String id, String name, double price, boolean available, String description) {
        super(id, name, price, available, description);
    }

    @Override
    public String displayDetails() {
        return "Bread: " + getName() + ", Price: $" + getPrice() + ", Available: " + isAvailable();
    }
}