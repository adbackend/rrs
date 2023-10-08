package com.ms.review.api;

import com.ms.review.api.request.CreateAndEditRestaurantRequest;
import com.ms.review.api.response.RestaurantDetailView;
import com.ms.review.api.response.RestaurantView;
import com.ms.review.model.RestaurantEntity;
import com.ms.review.service.RestaurantService;
import com.ms.review.service.ReviewService;
import com.ms.review.service.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantApi {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping("/restaurants")
    public List<RestaurantView> getRestaurants(){

        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/restaurant/{restaurantsId}")
    public RestaurantDetailView getRestaurant(@PathVariable Long restaurantsId){

        return restaurantService.getRestaurantDetail(restaurantsId);
    }

    @PostMapping("/restaurant")
    public void createRestaurant(@RequestBody CreateAndEditRestaurantRequest request){

        restaurantService.createRestaurant(request);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void editRestaurant(@PathVariable Long restaurantId, @RequestBody CreateAndEditRestaurantRequest request){
        restaurantService.editRestaurant(restaurantId, request);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteRestaurant(@PathVariable Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
    }

    @GetMapping("/restaurant/{restaurantId}/reviews")
    public ReviewDTO getRestaurantReviews(@PathVariable("restaurantId") Long restaurantId,
                                          @RequestParam("RequestParam") Integer offset,
                                          @RequestParam("limit") Integer limit){

        return reviewService.getRestaurantReview(restaurantId, PageRequest.of(offset/limit, limit));
    }

}
