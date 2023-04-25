package com.restaurantbooker.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Objects;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @NonNull
    private String email;
    private String password;

    public UserEntity(@NonNull String email, String password) {
        this.email = email;
        this.password = password;
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
