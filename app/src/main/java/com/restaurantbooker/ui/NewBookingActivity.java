package com.restaurantbooker.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.restaurantbooker.R;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.database.BookingDao;
import com.restaurantbooker.data.entities.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewBookingActivity extends AppCompatActivity {
    private EditText etRestaurantName;
    private EditText etPhoneNumber;
    private EditText etDate;
    private EditText etTime;
    private Button btnSave;
    private Button btnCancel;

    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbooking);

        etRestaurantName = findViewById(R.id.et_restaurant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etDate = findViewById(R.id.et_date);
        etTime = findViewById(R.id.et_time);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        bookingDao = AppDatabase.getInstance(this).bookingDao();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBooking();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void saveBooking() {
        String restaurantName = etRestaurantName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();

        new SaveBookingTask().execute(new Booking(restaurantName, phoneNumber, date, time));
    }

    private class SaveBookingTask extends AsyncTask<Booking, Void, Long> {

        @Override
        protected Long doInBackground(Booking... bookings) {
            if (bookings.length != 1) {
                return -1L;
            }
            try {
                return bookingDao.insert(bookings[0]);
            } catch (Exception e) {
                Log.e("NewBookingActivity", "Error saving booking", e);
                return -1L;
            }
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id > 0) {
                Log.i("NewBookingActivity", "Booking saved with ID: " + id);
                Toast.makeText(NewBookingActivity.this, "Booking saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.e("NewBookingActivity", "Error saving booking: ID not generated");
                Toast.makeText(NewBookingActivity.this, "Error saving booking", Toast.LENGTH_SHORT).show();
            }
        }
    }
}