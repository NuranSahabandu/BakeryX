package com.bakeryshehan.demo.controller;

import com.bakeryshehan.demo.model.*;
import com.bakeryshehan.demo.service.StaffService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/dashboard")
    public String staffDashboard() {
        return "forward:/staff-dashboard.html";
    }

    @GetMapping("/profile/{id}")
    public String staffProfile(@PathVariable String id) {
        return "forward:/staff-profile.html";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Staff staff) {
        staffService.updateStaff(staff);
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/admin")
    public String adminPanel() {
        return "forward:/admin-panel.html";
    }

    @PostMapping("/add")
    public String addStaff(@RequestParam String name, @RequestParam String email, @RequestParam String role) {
        int maxId = staffService.getAllStaff().stream()
            .mapToInt(s -> {
                try { return Integer.parseInt(s.getId()); } catch (Exception e) { return 0; }
            })
            .max().orElse(0);
        String id = String.valueOf(maxId + 1);
        Staff staff;
        switch (role) {
            case "ADMIN" -> staff = new Admin(id, name, email);
            case "BAKER" -> staff = new Baker(id, name, email);
            case "DELIVERY" -> staff = new DeliveryStaff(id, name, email);
            default -> staff = null;
        }
        if (staff != null) staffService.addStaff(staff);
        return "redirect:/staff/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteStaff(@PathVariable String id) {
        staffService.deleteStaff(id);
        return "redirect:/staff/admin";
    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam String id, @RequestParam String role) {
        Staff staff = staffService.getStaffById(id);
        if (staff != null) {
            staff.setRole(role);
            staffService.updateStaff(staff);
        }
        return "redirect:/staff/admin";
    }

    @GetMapping("/api/list")
    public List<Staff> apiList() {
        return staffService.getAllStaff();
    }
}
