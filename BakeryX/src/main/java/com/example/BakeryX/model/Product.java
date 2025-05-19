package com.bakery.productmanagement.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cake.class, name = "cake"),
        @JsonSubTypes.Type(value = Bread.class, name = "bread"),
        @JsonSubTypes.Type(value = Beverage.class, name = "beverage")
})
public abstract class Product {
    private String id;
    private String name;
    private double price;
    private boolean available;
    private String description;

    public Product() {
        // Default constructor required for Jackson
    }

    public Product(String id, String name, double price, boolean available, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public abstract String displayDetails();
}