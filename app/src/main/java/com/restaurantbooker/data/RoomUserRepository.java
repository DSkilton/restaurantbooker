package com.restaurantbooker.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.restaurantbooker.data.dao.UserDao;
import com.restaurantbooker.data.database.AppDatabase;
import com.restaurantbooker.data.entities.UserEntity;
import com.restaurantbooker.user.User;

public class RoomUserRepository implements UserRepository {
    private UserDao userDao;

    public RoomUserRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }

    @Override
    public LiveData<User> loginUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<UserEntity> signupUser(String email, String password) {
        return null;
    }

    @Override
    public LiveData<UserEntity> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public LiveData<Integer> updateUser(UserEntity user) {
        MutableLiveData<Integer> updateResult = new MutableLiveData<>();
        new UpdateUserAsyncTask(userDao, updateResult).execute(user);
        return updateResult;
    }

    @Override
    public LiveData<Integer> deleteUser(UserEntity user) {
        return null;
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
            return userDao.insertUser(users[0]);
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
        private MutableLiveData<Integer> deleteResult;

        public DeleteUserAsyncTask(UserDao userDao, MutableLiveData<Integer> deleteResult) {
            this.userDao = userDao;
            this.deleteResult = deleteResult;
        }

        @Override
        protected Integer doInBackground(UserEntity... users) {
            return userDao.deleteUser(users[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            deleteResult.setValue(result);
        }
    }
}