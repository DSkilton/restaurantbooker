package com.restaurantbooker.booking;

import com.restaurantbooker.restaurant.Restaurant;
import com.restaurantbooker.user.User;

import java.util.Date;

public class BookingManager {
    public Booking createBooking(User user, Restaurant restaurant, Date date, int guests) {
        return new Booking(user, restaurant, date, guests);
    }
}
