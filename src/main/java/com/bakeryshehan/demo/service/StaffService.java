package com.bakeryshehan.demo.service;

import com.bakeryshehan.demo.model.*;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class StaffService {
    private final Path dataFilePath;

    public StaffService() {
        // Initialize the data file path in user's home directory
        String userHome = System.getProperty("user.home");
        Path dataDir = Paths.get(userHome, "bakery-data");
        this.dataFilePath = dataDir.resolve("staff.txt");
    }

    @PostConstruct
    public void init() throws IOException {
        // Create directories and file if they don't exist
        Files.createDirectories(dataFilePath.getParent());
        if (!Files.exists(dataFilePath)) {
            Files.writeString(dataFilePath, "# Each line: id,name,email,role\n");
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(dataFilePath);
            for (String line : lines) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    continue;
                }
                String id = parts[0];
                String name = parts[1];
                String email = parts[2];
                String role = parts[3];
                Staff staff = switch (role) {
                    case "ADMIN" -> new Admin(id, name, email);
                    case "BAKER" -> new Baker(id, name, email);
                    case "DELIVERY" -> new DeliveryStaff(id, name, email);
                    default -> null;
                };
                if (staff != null) staffList.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public void addStaff(Staff staff) {
        try {
            String staffEntry = String.format("%s,%s,%s,%s%n",
                staff.getId(),
                staff.getName(),
                staff.getEmail(),
                staff.getRole());
            Files.writeString(dataFilePath, staffEntry, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff updatedStaff) {
        List<Staff> staffList = getAllStaff();
        try {
            StringBuilder content = new StringBuilder("# Each line: id,name,email,role\n");
            for (Staff staff : staffList) {
                if (staff.getId().equals(updatedStaff.getId())) {
                    staff = updatedStaff;
                }
                content.append(String.format("%s,%s,%s,%s%n",
                    staff.getId(),
                    staff.getName(),
                    staff.getEmail(),
                    staff.getRole()));
            }
            Files.writeString(dataFilePath, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(String id) {
        List<Staff> staffList = getAllStaff();
        try {
            StringBuilder content = new StringBuilder("# Each line: id,name,email,role\n");
            for (Staff staff : staffList) {
                if (!staff.getId().equals(id)) {
                    content.append(String.format("%s,%s,%s,%s%n",
                        staff.getId(),
                        staff.getName(),
                        staff.getEmail(),
                        staff.getRole()));
                }
            }
            Files.writeString(dataFilePath, content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Staff getStaffById(String id) {
        return getAllStaff().stream()
                .filter(staff -> staff.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
