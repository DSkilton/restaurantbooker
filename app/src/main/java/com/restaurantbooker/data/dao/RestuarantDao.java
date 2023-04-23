package com.restaurantbooker.data.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.restaurantbooker.restaurant.Restaurant;

import java.util.List;

@Dao
public interface RestuarantDao {
    @Query("SELECT * FROM restaurants")
    List<Restaurant> getRestaurants();
}
