package com.bakeryshehan.demo.model;

public class Admin extends Staff {
    public Admin(String id, String name, String email) {
        super(id, name, email, "ADMIN");
    }
    // Admin-only methods can be added here
}
