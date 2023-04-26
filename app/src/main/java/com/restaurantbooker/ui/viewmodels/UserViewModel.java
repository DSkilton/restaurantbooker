package com.restaurantbooker.ui.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.UserRepository;
import com.restaurantbooker.data.entities.UserEntity;

public class UserViewModel extends ViewModel{
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserEntity> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public LiveData<Long> insertUser(UserEntity user) {
        Log.d("UserViewModel", "Inserting user: " + user.getEmail());
        LiveData<Long> newRowId = userRepository.insertUser(user);
        newRowId.observeForever(rowId -> {
            if (rowId != null && rowId > 0) {
                Log.d("UserViewModel", "Inserted user with ID: " + rowId);
            } else {
                Log.d("UserViewModel", "Failed to insert user");
            }
        });
        return newRowId;
    }
}