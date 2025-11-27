package com.example.demo.services;

import com.example.demo.entity.Review;
import com.example.demo.model.ReviewData;
import com.example.demo.repository.ReviewInterface;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    ReviewInterface reviewInterface;

    public String getReviewData(ReviewData reviewData){
        Review review = new Review();
        review.setReview(reviewData.getReview());
        reviewInterface.save(review);
        Review reviewFromDb =reviewInterface.getReviewData();
        JsonObject objcet = new JsonObject();
        objcet.addProperty("id",reviewFromDb.getId());
        objcet.addProperty("review",reviewFromDb.getReview());
        return objcet.toString();
    }
}
