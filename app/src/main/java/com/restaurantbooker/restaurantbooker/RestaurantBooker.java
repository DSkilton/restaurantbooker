package com.restaurantbooker.restaurantbooker;

import android.app.Application;
import android.content.Context;

public class RestaurantBooker extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        RestaurantBooker.context =getApplicationContext();
    }

    public static Context getAppContext() {
        return RestaurantBooker.getAppContext();
    }
}
