package com.restaurantbooker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.RoomUserRepository;
import com.restaurantbooker.data.UserRepository;
import com.restaurantbooker.data.entities.UserEntity;

public class DashboardActivity extends AppCompatActivity {
    private Button btnViewBookings;
    private Button btnNewBooking;
    private Button btnEditBooking;
    private Button btnEditAccount;
    private Button btnLogout;
    private UserRepository userRepository;
    private UserEntity currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnViewBookings = findViewById(R.id.btn_view_bookings);
        btnNewBooking = findViewById(R.id.btn_make_booking);
        btnEditBooking = findViewById(R.id.btn_edit_booking);
        btnEditAccount = findViewById(R.id.btn_edit_account);
        btnLogout = findViewById(R.id.btn_logout);

        // Initialize the UserRepository instance
        userRepository = new RoomUserRepository(getApplication());

        // Retrieve the user email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("user_email", null);

        userRepository.getUserByEmail(userEmail).observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(UserEntity userEntity) {
                if (userEntity != null) {
                    currentUser = userEntity;
                    Log.d("DashboardActivity", "Fetched user with email: " + userEntity.getEmail());
                } else {
                    Log.e("DashboardActivity", "Error fetching user with email: " + userEmail);
                }
            }
        });

        btnViewBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ViewBookingsActivity.class);
                startActivity(intent);
            }
        });

        btnNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NewBookingActivity.class);
                startActivity(intent);
            }
        });

        btnEditBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EditBookingActivity.class);
                startActivity(intent);
            }
        });

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    Log.d("DashboardActivity", "currentUser is null");
                    Toast.makeText(DashboardActivity.this, "User not found. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    String email = currentUser.getEmail();
                    String password = currentUser.getPassword();
                    Log.d("DashboardActivity", "email: " + email + ", password: " + password);
                    Intent intent = new Intent(DashboardActivity.this, EditAccountActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_email");
                editor.apply();

                Intent intent = new Intent(DashboardActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
