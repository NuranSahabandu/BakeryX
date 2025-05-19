package com.bakery.productmanagement.model;

public class Cake extends Product {

    public Cake() {

    }

    public Cake(String id, String name, double price, boolean available, String description) {
        super(id, name, price, available, description);
    }

    @Override
    public String displayDetails() {
        return "Cake: " + getName() + ", Price: $" + getPrice() + ", Available: " + isAvailable();
    }
}