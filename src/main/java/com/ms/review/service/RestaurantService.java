package com.ms.review.service;

import com.ms.review.api.request.CreateAndEditRestaurantRequest;
import com.ms.review.api.response.RestaurantDetailView;
import com.ms.review.api.response.RestaurantView;
import com.ms.review.model.MenuEntity;
import com.ms.review.model.RestaurantEntity;
import com.ms.review.repository.MenuRepository;
import com.ms.review.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public RestaurantEntity createRestaurant(CreateAndEditRestaurantRequest request){
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updateAt(ZonedDateTime.now())
                .build();

        restaurantRepository.save(restaurant);

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createAt(ZonedDateTime.now())
                    .updateAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });



        return restaurant;
    }

    @Transactional
    public void editRestaurant(Long restaurantId, CreateAndEditRestaurantRequest request){

        RestaurantEntity restaurant =
                restaurantRepository.findById(restaurantId).orElseThrow(()-> new RuntimeException("없는 아이디 입니다."));

        restaurant.changeNameAndAddress(request.getName(), request.getAddress());

        restaurantRepository.save(restaurant);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        menuRepository.deleteAll(menus);

        request.getMenus().forEach((menu) -> {
            MenuEntity menuEntity = MenuEntity.builder()

                    .restaurantId(restaurantId)
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createAt(ZonedDateTime.now())
                    .updateAt(ZonedDateTime.now())
                    .build();

            menuRepository.save(menuEntity);
        });
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId){

        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurantRepository.delete(restaurantEntity);

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
    }

    @Transactional(readOnly = true)
    public List<RestaurantView> getAllRestaurants(){

        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map((restaurant) -> RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createAt(restaurant.getCreatedAt())
                .updateAt(restaurant.getUpdateAt())
                .build()
                ).toList();
    }

    @Transactional(readOnly = true)
    public RestaurantDetailView getRestaurantDetail(Long restaurantId){

        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        return RestaurantDetailView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createAt(restaurant.getCreatedAt())
                .updateAt(restaurant.getUpdateAt())
                .menus(menus.stream().map((menu)->RestaurantDetailView.Menu.builder()
                        .id(menu.getId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .createAt(menu.getCreateAt())
                        .updateAt(menu.getUpdateAt())
                        .build()
                ).toList())
                .build();
    }
}
