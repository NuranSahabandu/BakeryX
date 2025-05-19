package com.example.BakeryX.repository;


import com.example.BakeryX.entity.CustomOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CakeData {
    private static final List<CustomOrder> cakeList = new ArrayList<>();

    static {
        cakeList.add(new CustomOrder(1L, "Chocolate Fudge", "Round", "Happy Birthday!", LocalDate.of(2025, 5, 20), "Pending"));
        cakeList.add(new CustomOrder(2L, "Vanilla Bean", "Square", "Congratulations!", LocalDate.of(2025, 5, 22), "Confirmed"));
        cakeList.add(new CustomOrder(3L, "Strawberry Shortcake", "Heart", "I Love You", LocalDate.of(2025, 5, 25), "Delivered"));
        cakeList.add(new CustomOrder(4L, "Lemon Raspberry", "Rectangle", "Thank You", LocalDate.of(2025, 5, 28), "Pending"));
        cakeList.add(new CustomOrder(5L, "Carrot Cake", "Bundt", "Welcome Home", LocalDate.of(2025, 5, 30), "Confirmed"));
    }

    public static List<CustomOrder> getCakeList() {
        return cakeList;
    }
}
