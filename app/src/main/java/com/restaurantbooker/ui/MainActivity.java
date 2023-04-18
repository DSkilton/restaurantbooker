package com.restaurantbooker.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.RestaurantRepository;
import com.restaurantbooker.data.RoomRestaurantRepository;
import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.UserRepository;
import com.restaurantbooker.restaurant.Restaurant;
import com.restaurantbooker.user.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RestaurantRepository restaurantRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantRepository = new RoomRestaurantRepository();
        loadRestaurants();
    }

    private void loadRestaurants(){
        List<Restaurant> restaurants = restaurantRepository.getRestaurants();
    }
}
