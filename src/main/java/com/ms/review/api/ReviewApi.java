package com.ms.review.api;

import com.ms.review.api.request.CreateReviewRequest;
import com.ms.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewApi {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review")
    public void createReview(@RequestBody CreateReviewRequest request){

        reviewService.createReview(request.getRestaurantId(), request.getContent(), request.getScore());
    }

    // 리뷰 삭제
    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
    }
}
