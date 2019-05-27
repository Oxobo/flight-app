package com.reservation.flight.repository;

import android.app.Application;

import com.reservation.flight.config.AppDatabase;
import com.reservation.flight.dao.UserDao;
import com.reservation.flight.model.User;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public User fetchUsersWithUsername (String username){
        return userDao.fetchUsersWithUsername(username);
    }

    public void insertUser(User user){
        userDao.insertUser(user);
    }



}
