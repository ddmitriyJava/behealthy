package com.example.behealthy.util;

@FunctionalInterface
public interface AuthCallback {
    void processAuthResult(boolean isSuccessful);
}
