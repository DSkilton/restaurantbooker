package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.database.BookingDao;
import com.restaurantbooker.data.entities.Booking;
import com.restaurantbooker.ui.adapters.BookingAdapter;

import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {

    private RecyclerView rvBookings;
    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings);

        rvBookings = findViewById(R.id.rv_bookings);

        bookingDao = AppDatabase.getInstance(this).bookingDao();

        // Load bookings from the database
        List<Booking> bookings = bookingDao.getAll();

        // Set up RecyclerView and adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBookings.setLayoutManager(layoutManager);

        BookingAdapter adapter = new BookingAdapter(bookings);
        rvBookings.setAdapter(adapter);
    }
}
