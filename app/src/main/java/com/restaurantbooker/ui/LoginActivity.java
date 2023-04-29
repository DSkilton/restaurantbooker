package com.restaurantbooker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    Button btnSignUp;

    private UserDao userDao;

    private boolean isLoginValid(String email, String password) {
        UserEntity user = userDao.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    private void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize EditTexts and Buttons
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);

        // Initialize UserDao
        userDao = AppDatabase.getInstance(this).userDao();

        // Set onClickListener for the Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (isLoginValid(email, password)) {
                    saveUserEmail(email);
                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for the Sign Up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
