package com.restuarantbooker.restaurant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String address;
    private String phone;

}
