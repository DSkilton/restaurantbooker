package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public class EditAccountActivity extends AppCompatActivity {
    private static final String TAG = "EditAccountActivity";
    private RoomUserRepository userRepository;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSave;
    private Button btnCancel;
    private UserEntity currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        userRepository = new RoomUserRepository(getApplication());

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        Log.d("EditAccountActivity", "email: " + email + ", password: " + password);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        // Load current user data into EditText fields
        loadUserData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
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
        Intent intent = getIntent();
        String currentUserEmail = intent.getStringExtra("user_email");
        userRepository.getUserByEmail(currentUserEmail).observe(this, user -> {
            currentUser = user;
            if (currentUser != null) {
                etEmail.setText(currentUser.getEmail());
                etPassword.setText(currentUser.getPassword());
            } else {
                // Handle the case when the current user's data is not found in the database
                Toast.makeText(this, "There's an issue with your account data. Please log in again.", Toast.LENGTH_LONG).show();
                // Navigate back to the login screen
                Intent welcomeIntent = new Intent(EditAccountActivity.this, WelcomeActivity.class);
                startActivity(welcomeIntent);
                finish();
            }
        });
    }

    private void saveUserData() {
        if (currentUser != null) {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Invalid email format. Please check your input.", Toast.LENGTH_SHORT).show();
                return;
            }

            currentUser.setEmail(email);
            currentUser.setPassword(password);
            userRepository.updateUser(currentUser, rowsUpdated -> {
                if (rowsUpdated > 0) {
                    Log.d(TAG, "User data saved: email = " + email + ", password = " + password);
                    Toast.makeText(this, "Account information updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error updating account information. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}