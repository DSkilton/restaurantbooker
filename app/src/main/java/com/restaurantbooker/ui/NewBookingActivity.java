package com.restaurantbooker.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.restaurantbooker.R;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.database.BookingDao;
import com.restaurantbooker.data.entities.Booking;
import java.time.LocalDateTime;

public class NewBookingActivity extends AppCompatActivity {

    private EditText etRestaurantName;
    private EditText etPhoneNumber;
    private EditText etDateTime;
    private Button btnSave;
    private Button btnCancel;

    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newbooking);

        etRestaurantName = findViewById(R.id.et_restaurant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etDateTime = findViewById(R.id.et_date_time);
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
        String dateTime = etDateTime.getText().toString();

        if (restaurantName.isEmpty() || phoneNumber.isEmpty() || dateTime.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
        Booking newBooking = new Booking(restaurantName, phoneNumber, dateTime);
        newBooking.setDateTimeInGMT(localDateTime);
        bookingDao.insert(newBooking);

        Toast.makeText(this, "Booking saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
