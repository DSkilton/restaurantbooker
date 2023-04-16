package com.restuarantbooker.booking;

import com.restuarantbooker.restaurant.Restaurant;
import com.restuarantbooker.user.User;

import java.util.Date;

public class BookingManager {
    public Booking createBooking(User user, Restaurant restaurant, Date date, int guests) {
        return new Booking(user, restaurant, date, guests);
    }
}
