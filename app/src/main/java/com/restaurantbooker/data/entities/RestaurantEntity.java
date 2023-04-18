package com.restaurantbooker.data.entities;

import android.app.Application;
import android.content.Context;

public class RestaurantEntity extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        RestaurantEntity.context =getApplicationContext();
    }

    public static Context getAppContext() {
        return RestaurantEntity.getAppContext();
    }
}
