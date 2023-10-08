package com.ms.review.service;

import com.ms.review.model.ReviewEntity;
import com.ms.review.repository.RestaurantRepository;
import com.ms.review.repository.ReviewRepository;
import com.ms.review.service.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final RestaurantRepository restaurantRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(Long restaurantId, String content, Double score){

        restaurantRepository.findById(restaurantId).orElseThrow();

        ReviewEntity review = ReviewEntity.builder()
                .restaurantId(restaurantId)
                .content(content)
                .score(score)
                .createAt(ZonedDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId){

        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow();

        reviewRepository.delete(review);

    }

    public ReviewDTO getRestaurantReview(Long restaurantId, Pageable page){

        Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId);
        Slice<ReviewEntity> reviewEntities = reviewRepository.findSliceByRestaurantId(restaurantId, page);

        return ReviewDTO.builder()
                .avgScore(avgScore)
                .reviews(reviewEntities.getContent())
                .page(ReviewDTO.ReviewDtoPage.builder()
                        .offset(page.getPageNumber() * page.getPageSize())
                        .limit(page.getPageSize())
                        .build()
                ).build();
    }

}
