package com.restaurantbooker.data.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @NonNull
    private String email;
    private String password;
    private String username;

    public UserEntity(@NonNull String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Ignore //room can ignore this constructor
    public UserEntity(@NonNull String email, String password, String username) {
        this(email, password);
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
