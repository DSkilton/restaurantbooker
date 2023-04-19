package com.restaurantbooker.data;

import androidx.lifecycle.LiveData;

import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public interface UserRepository {
    LiveData<User> loginUser(String email, String password);
    LiveData<UserEntity> signupUser(String email, String password);
    LiveData<UserEntity> getUserByEmail(String email);
    LiveData<Integer> updateUser(UserEntity user);
    LiveData<Integer> deleteUser(UserEntity user);


}
