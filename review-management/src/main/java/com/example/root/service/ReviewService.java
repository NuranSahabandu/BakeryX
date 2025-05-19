package com.example.root.service;

import com.example.root.modal.Review;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ReviewService {
    //txt file path
    private final File file = new File("C:\\Users\\malsh\\OneDrive\\Desktop\\review-management\\review-management\\data\\reviews.txt"); //set your file path

    //get all review list
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        if (!file.exists()) return reviews;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::", -1);
                if (parts.length == 4) {
                    Review r = new Review();
                    r.setId(parts[0]);
                    r.setCustomerName(parts[1]);
                    r.setProduct(parts[2]);
                    r.setMessage(parts[3]);
                    reviews.add(r);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    //save order to the file
    public void saveReview(Review review) {
        List<Review> reviews = getAllReviews();
        reviews.add(review);
        writeToFile(reviews);
    }

    //delete a review
    public void deleteReview(String id) {
        List<Review> reviews = getAllReviews();
        reviews.removeIf(r -> r.getId().equals(id));
        writeToFile(reviews);
    }

    //update review
    public void updateReview(Review updated) {
        List<Review> reviews = getAllReviews();
        for (Review r : reviews) {
            if (r.getId().equals(updated.getId())) {
                r.setCustomerName(updated.getCustomerName());
                r.setProduct(updated.getProduct());
                r.setMessage(updated.getMessage());
                break;
            }
        }
        writeToFile(reviews);
    }

    //file write logic
    private void writeToFile(List<Review> reviews) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Review r : reviews) {
                bw.write(r.getId() + "::" + r.getCustomerName() + "::" + r.getProduct() + "::" + r.getMessage());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
