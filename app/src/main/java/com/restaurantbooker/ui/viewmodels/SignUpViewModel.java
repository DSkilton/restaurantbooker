package com.restaurantbooker.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.UserRepository;
import com.restaurantbooker.data.entities.UserEntity;

public class SignUpViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        userRepository = new RoomUserRepository(application);
    }

    public LiveData<UserEntity> signUpUser(String email, String password) {
        return userRepository.signupUser(email, password);
    }
}
