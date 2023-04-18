package com.restaurantbooker.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.user.User;

public class RoomUserRepository implements UserRepository {
    private UserDao userDao;

    public RoomUserRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao;
    }

    public RoomUserRepository() {

    }

    @Override
    public LiveData<User> loginUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<User> signupUser(String email, String password) {
        return null;
    }
}
