package com.ms.review.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class CreateReviewRequest {

    private final Long restaurantId;
    private final String content;
    private final Double score;
}
