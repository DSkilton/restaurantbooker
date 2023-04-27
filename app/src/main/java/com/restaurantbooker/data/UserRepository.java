package com.restaurantbooker.data;

import androidx.lifecycle.LiveData;

import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public interface UserRepository {
        LiveData<Long> signupUser(UserEntity user);
        LiveData<UserEntity> getUserByEmail(String email);
        LiveData<Long> insertUser(UserEntity user);
        LiveData<Long> updateUser(UserEntity user);
        LiveData<Long> deleteUser(UserEntity user);
        LiveData<UserEntity> signupUser(String email, String password);
}
