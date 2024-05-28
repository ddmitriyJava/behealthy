package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnUserAddedListener;
import com.example.behealthy.dao.util.OnUserAuthenticatedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.model.User;

public interface UserDAO {
    void addUser(User user, OnUserAddedListener onUserAddedListener);
    void updateUser(String field, Object object);
    void getUserByEmail(String userEmail, OnUserLoadedListener onUserLoadedListener);
    void authenticateUser(String userEmail, String userPassword, OnUserAuthenticatedListener onUserAuthenticatedListener);
    void logOutUser();
}
