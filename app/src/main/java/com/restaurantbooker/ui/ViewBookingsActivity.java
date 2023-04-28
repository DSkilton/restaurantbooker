package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.database.BookingDao;
import com.restaurantbooker.data.entities.Booking;
import com.restaurantbooker.ui.adapters.BookingAdapter;

import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {
    private RecyclerView rvBookings;
    private BookingDao bookingDao;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings);

        rvBookings = findViewById(R.id.rv_bookings);
        bookingDao = AppDatabase.getInstance(this).bookingDao();

        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewBookingsActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Load bookings from the database
        new LoadBookingsTask().execute();

    }


        private class LoadBookingsTask extends AsyncTask<Void, Void, List<Booking>> {

        @Override
        protected List<Booking> doInBackground(Void... voids) {
            return bookingDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Booking> bookings) {
            // Set up RecyclerView and adapter
            LinearLayoutManager layoutManager = new LinearLayoutManager(ViewBookingsActivity.this);
            rvBookings.setLayoutManager(layoutManager);

            BookingAdapter adapter = new BookingAdapter(bookings);
            rvBookings.setAdapter(adapter);
        }
    }
}
