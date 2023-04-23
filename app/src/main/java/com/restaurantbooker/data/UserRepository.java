package com.restaurantbooker.data;

import androidx.lifecycle.LiveData;
import androidx.room.solver.query.result.LiveDataQueryResultBinder;

import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public interface UserRepository {
    LiveData<User> loginUser(String email, String password);
    LiveData<User> signupUser(String email, String password);
    LiveData<UserEntity> getUserByEmail(String email);
    LiveData<Integer> updateUser(UserEntity user);
    LiveData<Integer> deleteUser(UserEntity user);


}
