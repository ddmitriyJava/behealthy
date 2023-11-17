package com.example.behealthy.dao.util;

import com.example.behealthy.entities.User;

@FunctionalInterface
public interface OnUserLoadedListener {
    void onUserLoaded(User user);
}
