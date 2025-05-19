package com.example.root.controller;

import com.example.root.modal.Review;
import com.example.root.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @GetMapping
    public List<Review> getAll() {
        return service.getAllReviews();
    }

    @PostMapping
    public void addReview(@RequestBody Review review) {
        review.setId(UUID.randomUUID().toString());
        service.saveReview(review);
    }

    @PutMapping
    public void updateReview(@RequestBody Review review) {
        service.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        service.deleteReview(id);
    }

    @GetMapping("/")
    public String home(){
        return "index";
    }
}
