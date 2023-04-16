package com.restuarantbooker.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.restaurantbooker.R;
import com.restuarantbooker.data.UserRepository;
import com.restuarantbooker.user.User;

public class LoginActivity extends AppCompatActivity {
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void loginUser(String email, String password) {
        LiveData<User> user = userRepository.loginUser(email, password);
        if (user != null) {
            // Proceed to the main activity
        } else {
            // Show error message
        }
    }

    private void signupUser(String email, String password) {
        LiveData<User> newUser = userRepository.signupUser(email, password);
        if (newUser != null) {
            // Proceed to the main activity
        } else {
            // Show error message
        }
    }
}
