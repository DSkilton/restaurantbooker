package com.restaurantbooker.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity(tableName = "bookings")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "restaurant_name")
    private String restaurantName;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    public Booking(String restaurantName, String phoneNumber, String dateTime) {
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTimeAsLocalDateTime() {
        return LocalDateTime.parse(dateTime);
    }

    public void setDateTimeFromLocalDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime.toString();
    }

    public LocalDateTime getDateTimeInGMT() {
        return getDateTimeAsLocalDateTime().atOffset(ZoneOffset.UTC).toLocalDateTime();
    }

    public void setDateTimeInGMT(LocalDateTime localDateTimeInGMT) {
        this.dateTime = localDateTimeInGMT.atOffset(ZoneOffset.UTC).toString();
    }
}
