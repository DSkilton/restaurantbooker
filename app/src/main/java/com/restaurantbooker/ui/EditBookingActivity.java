package com.restaurantbooker.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantbooker.R;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.database.BookingDao;
import com.restaurantbooker.data.models.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EditBookingActivity extends AppCompatActivity {

    private Spinner spinnerBookings;
    private EditText etRestaurantName;
    private EditText etPhoneNumber;
    private EditText etDateOfReservation;
    private EditText etTimeOfReservation;
    private Button btnSave;
    private Button btnCancel;
    private Button btnDelete;

    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbooking);

        spinnerBookings = findViewById(R.id.spinner_bookings);
        etRestaurantName = findViewById(R.id.et_restaurant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etDateOfReservation = findViewById(R.id.et_reservation_date);
        etTimeOfReservation = findViewById(R.id.et_reservation_time);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        bookingDao = AppDatabase.getInstance(this).bookingDao();

        // load bookings into spinner
        loadBookings();

        // set onClickListeners for buttons
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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBooking();
            }
        });
    }

    private void deleteBooking() {
        // Get the selected booking
        final Booking selectedBooking = (Booking) spinnerBookings.getSelectedItem();

        // Show a confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(EditBookingActivity.this);
        builder.setMessage("Are you sure you want to delete this booking?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the selected booking from the database
                bookingDao.delete(selectedBooking);

                // Show a toast message to confirm deletion
                Toast.makeText(EditBookingActivity.this, "Booking deleted", Toast.LENGTH_SHORT).show();

                // Refresh the bookings list in the spinner
                loadBookings();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void loadBookings() {
        // retrieve bookings from database
        List<Booking> bookings = bookingDao.getAll();

        // if no bookings exist, show a message
        if (bookings.isEmpty()) {
            TextView tvNoBookings = findViewById(R.id.tv_no_bookings);
            tvNoBookings.setVisibility(View.VISIBLE);
            return;
        }

        // populate spinner with bookings
        ArrayAdapter<Booking> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookings.setAdapter(adapter);

        // set spinner onItemSelectedListener to populate EditTexts with selected booking details
        spinnerBookings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Booking selectedBooking = (Booking) parent.getItemAtPosition(position);
                etRestaurantName.setText(selectedBooking.getRestaurantName());
                etPhoneNumber.setText(selectedBooking.getPhoneNumber());

                LocalDateTime dateTimeInGMT = selectedBooking.getDateTimeInGMT();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                etDateOfReservation.setText(dateTimeInGMT.format(dateFormatter));
                etTimeOfReservation.setText(dateTimeInGMT.format(timeFormatter));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private void saveBooking() {
        // get selected booking
        Booking selectedBooking = (Booking) spinnerBookings.getSelectedItem();

        // update booking details
        selectedBooking.setRestaurantName(etRestaurantName.getText().toString());
        selectedBooking.setPhoneNumber(etPhoneNumber.getText().toString());

        String dateInput = etDateOfReservation.getText().toString();
        String timeInput = etTimeOfReservation.getText().toString();

        // Parse input date and time, then set them as LocalDateTime in GMT
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate inputDate = LocalDate.parse(dateInput, dateFormatter);
            LocalTime inputTime = LocalTime.parse(timeInput, timeFormatter);

            LocalDateTime inputDateTime = LocalDateTime.of(inputDate, inputTime);
            selectedBooking.setDateTimeInGMT(inputDateTime);

            // update booking in the database
            bookingDao.update(selectedBooking);

            // show success message
            Toast.makeText(this, "Booking updated successfully!", Toast.LENGTH_SHORT).show();
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date or time format. Please check your input.", Toast.LENGTH_SHORT).show();
        }
    }
}
