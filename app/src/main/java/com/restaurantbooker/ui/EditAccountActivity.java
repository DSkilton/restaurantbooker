package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public class EditAccountActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private Button btnSave;
    private Button btnCancel;

    private UserDao userDao;
    private UserEntity currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        userDao = AppDatabase.getInstance(this).userDao();

        // Load current user data into EditText fields
        loadUserData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadUserData() {
        // Get the current user's email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String currentUserEmail = sharedPreferences.getString("user_email", null);

        if (currentUserEmail != null) {
            currentUser = userDao.findByEmail(currentUserEmail);
            if (currentUser != null) {
                etUsername.setText(currentUser.getUsername());
                etEmail.setText(currentUser.getEmail());
            }
        } else {
            // Handle the case when the current user's email is not found in shared preferences
            Toast.makeText(this, "There's an issue with your account data. Please log in again.", Toast.LENGTH_LONG).show();
            // Navigate back to the login screen
            Intent intent = new Intent(EditAccountActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void saveUserData() {
        if (currentUser != null) {
            currentUser.setUsername(etUsername.getText().toString());
            currentUser.setEmail(etEmail.getText().toString());
            userDao.updateUser(currentUser);
        }
    }   
}
