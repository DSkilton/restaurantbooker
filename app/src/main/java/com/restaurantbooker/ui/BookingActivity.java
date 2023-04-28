package com.restaurantbooker.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantbooker.R;
import com.restaurantbooker.booking.BookingManager;

public class BookingActivity extends AppCompatActivity {
    private BookingManager bookingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingManager = new BookingManager();

        // Handle booking interactions
    }

}
