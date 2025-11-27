package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewInterface extends JpaRepository<Review, Long> {

    @Query(value = "select * from review",nativeQuery = true)
    public Review getReviewData();

}
