package com.restaurantbooker.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomUserRepository implements UserRepository {
    private UserDao userDao;
    private Executor executor;

    public RoomUserRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        executor = Executors.newSingleThreadExecutor();
        Log.d("RoomUserRepository", "RoomUserRepository initialized");
        executor.execute(() -> {
            UserEntity testUser = new UserEntity("test@example.com", "testpassword");
            userDao.insertUser(testUser);
            Log.d("RoomUserRepository", "Inserted test user: " + testUser);
        });
    }

    public void addUser(UserEntity user){
        executor.execute(() -> {
            if(emailExists(user.getEmail())){
                Log.e("RoomUserRepository", "Email already exists: " + user.getEmail());
            } else{
                userDao.insertUser(user);
            }
        });
    }

    public LiveData<Long> insertUser(UserEntity user) {
        MutableLiveData<Long> insertResult = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                long id = userDao.insertUser(user);
                Log.d("RoomUserRepository", "User inserted with ID: " + id);
                insertResult.postValue(id);
            } catch (Exception e) {
                Log.e("RoomUserRepository", "Error inserting user", e);
                insertResult.postValue(-1L);
            }
        });
        return insertResult;
    }

    public LiveData<Long> updateUser(UserEntity user) {
        MutableLiveData<Long> updateResult = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                int rowsUpdated = userDao.updateUser(user);
                Log.d("RoomUserRepository", "User updated: " + user);
                updateResult.postValue((long)rowsUpdated);
            } catch (Exception e) {
                Log.e("RoomUserRepository", "Error updating user", e);
                updateResult.postValue(0L);
            }
        });
        return updateResult;
    }

    public LiveData<Long> deleteUser(UserEntity user) {
        MutableLiveData<Long> deleteResult = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                int rowsDeleted = userDao.deleteUser(user);
                Log.d("RoomUserRepository", "User deleted: " + user);
                deleteResult.postValue((long) rowsDeleted);
            } catch (Exception e) {
                Log.e("RoomUserRepository", "Error deleting user", e);
                deleteResult.postValue(0L);
            }
        });
        return deleteResult;
    }

    @Override
    public LiveData<UserEntity> signupUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<Long> signupUser(UserEntity user) {
        return insertUser(user);
    }

    @Override
    public LiveData<UserEntity> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    private boolean emailExists(String email) {
        return userDao.emailExists(email);
    }
}
