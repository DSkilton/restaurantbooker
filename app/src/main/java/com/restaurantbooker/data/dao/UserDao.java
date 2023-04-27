package com.restaurantbooker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurantbooker.data.entities.UserEntity;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email")
    LiveData<UserEntity> getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    LiveData<UserEntity> loginUser(String email, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertUser(UserEntity user);

    @Update
    int updateUser(UserEntity user);

    @Delete
    int deleteUser(UserEntity user);

    @Query("SELECT COUNT(*) > 0 FROM users WHERE email = :email")
    boolean emailExists(String email);

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity findByEmail(String email);
}
