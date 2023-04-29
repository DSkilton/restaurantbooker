package com.restaurantbooker.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.restaurantbooker.data.dao.RestuarantDao;
import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.entities.Booking;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.restaurant.Restaurant;
import com.restaurantbooker.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@TypeConverters({Converters.class})
@Database(entities = {UserEntity.class, Restaurant.class, Booking.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "restaurant_booked_db";
    private static AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);


    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract RestuarantDao restuarantDao();
    public abstract BookingDao bookingDao();

    @Override
    public void clearAllTables() {

    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
