package com.bakeryshehan.demo.service;

import com.bakeryshehan.demo.model.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;

@Service
public class StaffService {
    private static final String STAFF_FILE = "src/main/resources/staff.txt";

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(STAFF_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STAFF_FILE, true))) {
            bw.write(staff.getId() + "," + staff.getName() + "," + staff.getEmail() + "," + staff.getRole());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff updatedStaff) {
        List<Staff> staffList = getAllStaff();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STAFF_FILE))) {
            bw.write("# Each line: id,name,email,role\n");
            for (Staff staff : staffList) {
                if (staff.getId().equals(updatedStaff.getId())) {
                    staff = updatedStaff;
                }
                bw.write(staff.getId() + "," + staff.getName() + "," + staff.getEmail() + "," + staff.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(String id) {
        List<Staff> staffList = getAllStaff();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STAFF_FILE))) {
            bw.write("# Each line: id,name,email,role\n");
            for (Staff staff : staffList) {
                if (!staff.getId().equals(id)) {
                    bw.write(staff.getId() + "," + staff.getName() + "," + staff.getEmail() + "," + staff.getRole());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Staff getStaffById(String id) {
        for (Staff staff : getAllStaff()) {
            if (staff.getId().equals(id)) return staff;
        }
        return null;
    }
}
