package com.restaurantbooker.data.entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity(tableName = "bookings")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "restaurant_name")
    private String restaurantName;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "date")
    private LocalDate date;
    @ColumnInfo(name = "time")
    private LocalTime time;

    public Booking(String restaurantName, String phoneNumber, LocalDate date, LocalTime time) {
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
    }

    public Booking(String restaurantName, String phoneNumber, String date, String time) {
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.date = parseDate(date);
        this.time = parseTime(time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = parseDate(date);
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    private static LocalDate parseDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            Log.e("Booking", "Invalid date format: " + date, e);
            return null;
        }
        return parsedDate;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = parseTime(time);
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    private static LocalTime parseTime(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedTime;

        try {
            parsedTime = LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            Log.e("Booking", "Invalid time format: " + time, e);
            return null;
        }

        return parsedTime;
    }

    @Override
    public String toString() {
        return restaurantName + " - " + date + " - " + time;
    }
}
