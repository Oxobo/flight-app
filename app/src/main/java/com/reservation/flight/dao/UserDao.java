package com.reservation.flight.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.reservation.flight.model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);
    @Update
    void updateUser(User user);
    @Delete
    void deleteUser(User user);
    @Query("SELECT * FROM user WHERE userID = :userID")
    User fetchOneUsersbyUserId(int userID);
    @Query("SELECT * FROM user WHERE username = :username")
    User fetchUsersWithUsername (String username);

}
