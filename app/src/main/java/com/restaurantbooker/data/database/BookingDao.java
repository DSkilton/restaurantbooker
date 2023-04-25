package com.restaurantbooker.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurantbooker.data.entities.Booking;

import java.util.List;

@Dao
public interface BookingDao {

    @Query("SELECT * FROM bookings")
    List<Booking> getAll();

    @Query("SELECT * FROM bookings WHERE id = :id")
    Booking getById(long id);

    @Insert
    long insert(Booking booking);

    @Update
    void update(Booking booking);

    @Delete
    void delete(Booking booking);
}
