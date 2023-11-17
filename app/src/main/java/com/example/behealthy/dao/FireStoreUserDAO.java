package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnUserAddedListener;
import com.example.behealthy.dao.util.OnUserAuthenticatedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.entities.HealthIndicators;
import com.example.behealthy.entities.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FireStoreUserDAO implements UserDAO {
    private CollectionReference collection;

    public FireStoreUserDAO() {
        FireStoreDAOFactory fireStoreDAOFactory = FireStoreDAOFactory.getInstance();
        collection = fireStoreDAOFactory.getCollection();
    }

    @Override
    public void addUser(User user, OnUserAddedListener onUserAddedListener) {
        collection.add(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onUserAddedListener.onUserAdded();
            } else {
                onUserAddedListener.onUserAddFailed();
            }
        });
    }

    @Override
    public void updateUser(String userEmail, String field, Object object) {
        collection
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userId = document.getId();
                        DocumentReference userDocumentRef = collection.document(userId);
                        userDocumentRef.update(field, object);
                    }
                });
    }

    @Override
    public void getUserByEmail(String userEmail, OnUserLoadedListener onUserLoadedListener) {
        collection
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String pib = document.getString("pib");
                            String email = document.getString("email");
                            String password = document.getString("password");
                            String diagnosis = document.getString("diagnosis");
                            List<HealthIndicators> healthIndicatorsList = document.toObject(User.class).getHealthIndicatorsList();

                            User user = new User(pib, email, password, diagnosis, healthIndicatorsList);
                            onUserLoadedListener.onUserLoaded(user);
                        }
                    }
                });
    }

    @Override
    public void authenticateUser(String userEmail, String userPassword, OnUserAuthenticatedListener onUserAuthenticatedListener) {
        collection
                .whereEqualTo("email", userEmail)
                .whereEqualTo("password", userPassword)
                .get()
                .addOnCompleteListener(task -> {
                    QuerySnapshot result = task.getResult();
                    if (result != null && !result.isEmpty()) {
                        onUserAuthenticatedListener.onUserAuthenticated();
                    } else {
                        onUserAuthenticatedListener.onUserAuthenticatedFailed();
                    }
                });
    }
}
