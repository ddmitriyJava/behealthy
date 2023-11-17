package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnUserAddedListener;
import com.example.behealthy.dao.util.OnUserAuthenticatedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.entities.User;

public interface UserDAO {
    void addUser(User user, OnUserAddedListener onUserAddedListener);
    void updateUser(String userEmail, String field, Object object);
    void getUserByEmail(String userEmail, OnUserLoadedListener onUserLoadedListener);
    void authenticateUser(String userEmail, String userPassword, OnUserAuthenticatedListener onUserAuthenticatedListener);
}
