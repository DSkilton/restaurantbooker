package com.restaurantbooker.data;

import static com.restaurantbooker.data.database.AppDatabase.databaseWriteExecutor;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

import java.sql.SQLClientInfoException;

public class RoomUserRepository implements UserRepository {
    private UserDao userDao;

    public RoomUserRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        Log.d("RoomUserRepository", "RoomUserRepository initialized");
        databaseWriteExecutor.execute(() -> {
            UserEntity testUser = new UserEntity("test@example.com", "testpassword");
            userDao.insertUser(testUser);
            Log.d("RoomUserRepository", "Inserted test user: " + testUser);
        });
    }

    public void addUser(UserEntity user){
        try{
            if(emailExists(user.getEmail())){

            } else{
                insertUser(user);
            }
        } catch (SQLClientInfoException e) {
            Log.e("RoomUserRepository", "SQLiteConstraintException while adding user: " + e.getMessage());
        }
    }

    @Override
    public LiveData<Long> insertUser(UserEntity user) {
        MutableLiveData<Long> insertResult = new MutableLiveData<>();
        databaseWriteExecutor.execute(() -> {
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

    @Override
    public LiveData<Integer> updateUser(UserEntity user) {
        MutableLiveData<Integer> updateResult = new MutableLiveData<>();
        new UpdateUserAsyncTask(userDao, updateResult).execute(user);
        return updateResult;
    }

    @Override
    public LiveData<Long> deleteUser(UserEntity user) {
        MutableLiveData<Long> deleteResult = new MutableLiveData<>();
        new DeleteUserAsyncTask(userDao, deleteResult).execute(user);
        return deleteResult;
    }

    @Override
    public LiveData<UserEntity> signupUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<Long> signupUser(UserEntity user) {
        MutableLiveData<Long> insertResult = new MutableLiveData<>();
        new InsertUserAsyncTask(userDao, insertResult).execute(user);
        return insertResult;
    }

    @Override
    public LiveData<UserEntity> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserEntity, Void, Long> {
        private UserDao userDao;
        private MutableLiveData<Long> insertResult;

        public InsertUserAsyncTask(UserDao userDao, MutableLiveData<Long> insertResult){
            this.userDao = userDao;
            this.insertResult = insertResult;
        }

        @Override
        protected Long doInBackground(UserEntity... users) {
            return userDao.insert(users[0]);
        }

        @Override
        protected void onPostExecute(Long result){
            insertResult.setValue(result);
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserEntity, Void, Integer>{
        private UserDao userDao;
        private MutableLiveData<Integer> updateResult;

        public UpdateUserAsyncTask(UserDao userDao, MutableLiveData<Integer> updateResult){
            this.userDao = userDao;
            this.updateResult = updateResult;
        }

        @Override
        protected Integer doInBackground(UserEntity... userEntities) {
            return null;
        }

        @Override
        protected void onPostExecute(Integer result){
            updateResult.setValue(result);
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<UserEntity, Void, Integer> {
        private UserDao userDao;
        private MutableLiveData<Long> deleteResult;

        public DeleteUserAsyncTask(UserDao userDao, MutableLiveData<Long> deleteResult) {
            this.userDao = userDao;
            this.deleteResult = deleteResult;
        }

        @Override
        protected Integer doInBackground(UserEntity... userEntities) {
            return null;
        }
    }
}