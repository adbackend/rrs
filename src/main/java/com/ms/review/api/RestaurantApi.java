package com.ms.review.api;

import com.ms.review.api.request.CreateAndEditRestaurantRequest;
import com.ms.review.model.RestaurantEntity;
import com.ms.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RestaurantApi {

    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String getRestaurants(){

        return "this is getRestaurants";
    }

    @GetMapping("/restaurant/{restaurantsId}")
    public String getRestaurant(@PathVariable Long restaurantsId){

        return "this is getRestaurants :  " + restaurantsId;
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
}
