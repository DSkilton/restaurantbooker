package com.restaurantbooker.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

public class EditBookingActivity extends AppCompatActivity {
    private Spinner spinnerBookings;
    private EditText etRestaurantName;
    private EditText etPhoneNumber;
    private TextView tvDate;
    private TextView tvTime;
    private Button btnSave;
    private Button btnCancel;
    private Button btnDelete;
    private Button btnBack;

    private BookingDao bookingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbooking);

        spinnerBookings = findViewById(R.id.spinner_bookings);
        etRestaurantName = findViewById(R.id.et_restaurant_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        tvDate = findViewById(R.id.tv_reservation_date);
        tvTime = findViewById(R.id.tv_reservation_time);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);
        btnBack = findViewById(R.id.btn_back);

        bookingDao = AppDatabase.getInstance(this).bookingDao();

        // load bookings into spinner
        new LoadBookingsTask().execute();

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBookingActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupSpinner() {
        List<Booking> bookings = new ArrayList<>();
        // Populate the list with your booking data

        Spinner spinnerBookings = findViewById(R.id.spinner_bookings);
        ArrayAdapter<Booking> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookings.setAdapter(adapter);
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

                LocalDate date = selectedBooking.getDate();
                LocalTime time = selectedBooking.getTime();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                tvDate.setText(date.format(dateFormatter));
                tvTime.setText(time.format(timeFormatter));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    private class LoadBookingsTask extends AsyncTask<Void, Void, List<Booking>> {

        @Override
        protected List<Booking> doInBackground(Void... voids) {
            return bookingDao.getAll();
        }

        @Override
        protected void onPostExecute(List<Booking> bookings) {
            loadBookings(bookings);
        }
    }

    private void loadBookings(List<Booking> bookings) {
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

                LocalDate date = selectedBooking.getDate();
                LocalTime time = selectedBooking.getTime();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                tvDate.setText(date.format(dateFormatter));
                tvTime.setText(time.format(timeFormatter));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DatePicker datePicker = findViewById(R.id.date_picker);
        datePicker.setVisibility(View.VISIBLE);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String formattedDate = String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year);
                tvDate.setText(formattedDate);
                datePicker.setVisibility(View.GONE);
            }
        });
    }

    public void showTimePickerDialog(View v) {
        TimePicker timePicker = findViewById(R.id.time_picker);
        timePicker.setVisibility(View.VISIBLE);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                tvTime.setText(formattedTime);
                timePicker.setVisibility(View.GONE);
            }
        });
    }
                private class SaveBookingTask extends AsyncTask<Booking, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Booking... bookings) {
            try {
                bookingDao.update(bookings[0]);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(EditBookingActivity.this, "Booking updated successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditBookingActivity.this, "Failed to update booking. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveBooking() {
        // get selected booking
        Booking selectedBooking = (Booking) spinnerBookings.getSelectedItem();

        // update booking details
        selectedBooking.setRestaurantName(etRestaurantName.getText().toString());

        String phoneNumber = etPhoneNumber.getText().toString();

        if (phoneNumber.length() < 11) {
            Toast.makeText(this, "Phone number must be at least 11 digits. Please check your input.", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedBooking.setPhoneNumber(etPhoneNumber.getText().toString());

        String dateInput = tvDate.getText().toString();
        String timeInput = tvTime.getText().toString();

        // Parse input date and time, then set them as LocalDateTime in GMT
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate inputDate = LocalDate.parse(dateInput, dateFormatter);
            LocalTime inputTime = LocalTime.parse(timeInput, timeFormatter);

            selectedBooking.setDate(inputDate);
            selectedBooking.setTime(inputTime);

            // show success message
            Toast.makeText(this, "Booking updated successfully!", Toast.LENGTH_SHORT).show();
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date or time format. Please check your input.", Toast.LENGTH_SHORT).show();
        }

        // Update the booking in the database using AsyncTask
        new SaveBookingTask().execute(selectedBooking);
    }

    private class DeleteBookingTask extends AsyncTask<Booking, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Booking... bookings) {
            try {
                bookingDao.delete(bookings[0]);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(EditBookingActivity.this, "Booking deleted", Toast.LENGTH_SHORT).show();
                new LoadBookingsTask().execute(); // Refresh the bookings list in the spinner
            } else {
                Toast.makeText(EditBookingActivity.this, "Failed to delete booking. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
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

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });
        builder.show();

        // Delete the selected booking from the database using AsyncTask
        new DeleteBookingTask().execute(selectedBooking);
    }
}
