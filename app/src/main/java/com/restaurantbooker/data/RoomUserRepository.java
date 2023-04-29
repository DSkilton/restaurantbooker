package com.restaurantbooker.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomUserRepository implements UserRepository {
    private static final String TAG = "RoomUserRepository";
    private MutableLiveData<UserEntity> userByEmail = new MutableLiveData<>();
    private UserDao userDao;
    private Executor executor;

    public interface UpdateUserCallback {
        void onUserUpdated(int rowsUpdated);
    }

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
                Log.d(TAG, "User inserted with ID: " + id);
                insertResult.postValue(id);
            } catch (Exception e) {
                Log.d(TAG, "Error inserting user", e);
                insertResult.postValue(-1L);
            }
        });
        return insertResult;
    }

    public void updateUser(UserEntity user, UpdateUserCallback callback) {
        new UpdateUserAsyncTask(userDao, callback).execute(user);
    }

    public LiveData<Long> updateUser(UserEntity user) {
        MutableLiveData<Long> updateResult = new MutableLiveData<>();
        executor.execute(() -> {
            try {
                int rowsUpdated = userDao.updateUser(user);
                Log.d(TAG, "User updated: " + user + ", updated rows: " + updateResult);
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
        Log.d("RoomUserRepository", "getUserByEmail called with email: " + email);
        new GetUserByEmailAsyncTask(userDao, userByEmail).execute(email);
        return userByEmail;
    }

    private boolean emailExists(String email) {
        return userDao.emailExists(email);
    }

    private static class GetUserByEmailAsyncTask extends AsyncTask<String, Void, UserEntity> {
        private UserDao userDao;
        private MutableLiveData<UserEntity> userByEmail;

        public GetUserByEmailAsyncTask(UserDao userDao, MutableLiveData<UserEntity> userByEmail) {
            this.userDao = userDao;
            this.userByEmail = userByEmail;
        }

        @Override
        protected UserEntity doInBackground(String... emails) {
            Log.d("RoomUserRepository", "doInBackground called with email: " + emails[0]);
            return userDao.findByEmail(emails[0]);
        }

        @Override
        protected void onPostExecute(UserEntity user) {
            Log.d("RoomUserRepository", "onPostExecute called with user: " + user);
            userByEmail.postValue(user);
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserEntity, Void, Void> {
        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserEntity, Void, Integer> {
        private UserDao userDao;
        private UpdateUserCallback callback;

        private UpdateUserAsyncTask(UserDao userDao, UpdateUserCallback callback) {
            this.userDao = userDao;
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(UserEntity... userEntities) {
            return userDao.updateUser(userEntities[0]);
        }

        @Override
        protected void onPostExecute(Integer rowsUpdated) {
            super.onPostExecute(rowsUpdated);
            callback.onUserUpdated(rowsUpdated);
        }
    }
}
