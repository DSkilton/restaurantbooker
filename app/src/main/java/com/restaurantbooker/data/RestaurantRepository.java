package com.restaurantbooker.data;

import com.restaurantbooker.restaurant.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    List<Restaurant> getRestaurants();
}
