package com.ms.review.api;

import com.ms.review.api.request.CreateAndEditRestaurantRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantApi {

    @GetMapping("/restaurants")
    public String getRestaurants(){

        return "this is getRestaurants";
    }

    @GetMapping("/restaurant/{restaurantsId}")
    public String getRestaurant(@PathVariable Long restaurantsId){

        return "this is getRestaurants :  " + restaurantsId;
    }

    @PostMapping("/restaurant")
    public String createRestaurant(@RequestBody CreateAndEditRestaurantRequest request){
        return "this is createRestaurant, name = " + request.getName() + ", address = " + request.getAddress()
                                        + ", menu[0].name = " + request.getMenus().get(0).getName();
    }

    @PutMapping("/restaurant/{restaurant}")
    public String editRestaurant(@PathVariable Long restaurantId, @RequestBody CreateAndEditRestaurantRequest request){
        return "this is editRestaurant , " + restaurantId + " name = " + request.getName() + ", address = " + request.getAddress();
    }

    @DeleteMapping("/restaurant/{restaurant}")
    public String deleteRestaurant(@PathVariable Long restaurantId){
        return "this is deleteRestaurant , " + restaurantId;
    }
}
