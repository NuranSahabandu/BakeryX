package com.bakery.productmanagement.model;

public class Beverage extends Product {

    public Beverage() {

    }

    public Beverage(String id, String name, double price, boolean available, String description) {
        super(id, name, price, available, description);
    }

    @Override
    public String displayDetails() {
        return "Beverage: " + getName() + ", Price: $" + getPrice() + ", Available: " + isAvailable();
    }
}