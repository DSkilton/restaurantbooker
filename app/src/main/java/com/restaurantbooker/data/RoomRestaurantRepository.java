package com.restaurantbooker.data;

import android.app.Application;

import com.restaurantbooker.data.dao.RestuarantDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.restaurant.Restaurant;

import java.util.List;

public class RoomRestaurantRepository implements RestaurantRepository {
    private RestuarantDao restuarantDao;

    public RoomRestaurantRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        restuarantDao = db.restuarantDao();
    }

    protected void getRestuarants(){

    }

    @Override
    public List<Restaurant> getRestaurants() {
        return null;
    }
}
