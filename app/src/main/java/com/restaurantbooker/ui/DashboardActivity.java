package com.restaurantbooker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantbooker.R;

public class DashboardActivity extends AppCompatActivity {
    private Button btnViewBookings;
    private Button btnNewBooking;
    private Button btnEditBooking;
    private Button btnEditAccount;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnViewBookings = findViewById(R.id.btn_view_bookings);
        btnNewBooking = findViewById(R.id.btn_make_booking);
        btnEditBooking = findViewById(R.id.btn_edit_booking);
        btnEditAccount = findViewById(R.id.btn_edit_account);
        btnLogout = findViewById(R.id.btn_logout);

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
                Intent intent = new Intent(DashboardActivity.this, EditAccountActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: implement logout functionality
            }
        });
    }
}
