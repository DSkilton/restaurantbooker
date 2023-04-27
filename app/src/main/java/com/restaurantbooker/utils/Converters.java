package com.restaurantbooker.utils;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    @TypeConverter
    public static LocalDate fromStringToDate(String value) {
        return value == null ? null : LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static String fromDateToString(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @TypeConverter
    public static LocalTime fromStringToTime(String value) {
        return value == null ? null : LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @TypeConverter
    public static String fromTimeToString(LocalTime time) {
        return time == null ? null : time.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
