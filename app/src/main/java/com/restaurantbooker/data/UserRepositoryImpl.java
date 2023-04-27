package com.restaurantbooker.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserRepositoryImpl implements UserRepository {
    private UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public LiveData<Long> signupUser(UserEntity user) {
        return null;
    }

    @Override
    public LiveData<UserEntity> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public LiveData<Long> updateUser(UserEntity user) {
        return null;
    }

    @Override
    public LiveData<Long> deleteUser(UserEntity user) {
        return null;
    }

    @Override
    public LiveData<UserEntity> signupUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<Long> insertUser(UserEntity user) {
        MutableLiveData<Long> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            long id = userDao.insertUser(user);
            result.postValue(id);
        });
        return result;
    }
}