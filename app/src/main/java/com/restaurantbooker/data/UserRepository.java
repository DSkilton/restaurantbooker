package com.restaurantbooker.data;

import androidx.lifecycle.LiveData;

import com.restaurantbooker.user.User;

public interface UserRepository {
    LiveData<User> loginUser(String email, String password);
    LiveData<User> signupUser(String email, String password);


}
