package com.jobApp.reviewms.Review.impl;


import com.jobApp.reviewms.Review.Review;
import com.jobApp.reviewms.Review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements com.jobApp.reviewms.Review.ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public Boolean addReview(Long companyId, Review review) {
        if(companyId != null && review !=  null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;

    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);

    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(review);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            reviewRepository.delete(review);
            return true;
        }
        else
            return false;
    }


}
