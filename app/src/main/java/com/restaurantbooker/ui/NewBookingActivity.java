package com.restaurantbooker.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
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

        if (restaurantName.isEmpty() || phoneNumber.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate phone number
        Pattern phonePattern = Pattern.compile("^0\\d{10}$");
        Matcher phoneMatcher = phonePattern.matcher(phoneNumber);
        if (!phoneMatcher.matches()) {
            Toast.makeText(this, "Invalid phone number. It must be 11 digits and start with 0", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate date
        date = etDate.getText().toString();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate;

        try {
            localDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date format. Use the format: DD-MM-YYYY", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate time
        time = etTime.getText().toString();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime;

        try {
            localTime = LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid time format. Use the format: HH:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        // Check if the date-time is not in the past
        if (localDateTime.isBefore(LocalDateTime.now())) {
            Toast.makeText(this, "Cannot book in the past. Please choose a future date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Booking newBooking = new Booking(restaurantName, phoneNumber, date, time);
            newBooking.setRestaurantName(restaurantName);
            newBooking.setPhoneNumber(phoneNumber);
            newBooking.setDate(date);
            newBooking.setTime(time);

            bookingDao.insert(newBooking);
            Toast.makeText(this, "Booking saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error saving booking", e);
            Toast.makeText(this, "Error saving booking", Toast.LENGTH_SHORT).show();
        }
    }
}