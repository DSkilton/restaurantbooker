package com.restaurantbooker.ui;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
    private TextView tvDate;
    private TextView tvTime;
    private Button btnSave;
    private Button btnCancel;

    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbooking);

        etRestaurantName = findViewById(R.id.et_restaurant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        tvDate = findViewById(R.id.tv_reservation_date);
        tvTime = findViewById(R.id.tv_reservation_time);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        bookingDao = AppDatabase.getInstance(this).bookingDao();

        tvDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showDatePickerDialog();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showTimePickerDialog();
            }
        });

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

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year);
                        tvDate.setText(date);
                    }
                },
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue() - 1,
                LocalDate.now().getDayOfMonth()
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = String.format("%02d:%02d", hourOfDay, minute);
                        tvTime.setText(time);
                    }
                },
                LocalTime.now().getHour(),
                LocalTime.now().getMinute(),
                true
        );
        timePickerDialog.show();
    }

    private void saveBooking() {
        String restaurantName = etRestaurantName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String date = tvDate.getText().toString();
        String time = tvTime.getText().toString();

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