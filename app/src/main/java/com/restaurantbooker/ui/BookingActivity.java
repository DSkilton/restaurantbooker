package com.restaurantbooker.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantbooker.R;
import com.restaurantbooker.booking.Booking;
import com.restaurantbooker.booking.BookingManager;
import com.restaurantbooker.restaurant.Restaurant;
import com.restaurantbooker.user.User;

import java.util.Date;

public class BookingActivity extends AppCompatActivity {
    private BookingManager bookingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bookingManager = new BookingManager();

        // Handle booking interactions
    }

    private void bookTable(User user, Restaurant restaurant, Date date, int guests) {
        Booking booking = bookingManager.createBooking(user, restaurant, date, guests);
    }
}
