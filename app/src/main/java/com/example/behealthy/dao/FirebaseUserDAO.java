package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnUserAddedListener;
import com.example.behealthy.dao.util.OnUserAuthenticatedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.model.HealthIndicators;
import com.example.behealthy.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.List;
import java.util.Objects;

public class FirebaseUserDAO implements UserDAO {
    private CollectionReference collection;
    private FirebaseAuth mAuth;

    public FirebaseUserDAO() {
        FirebaseDAOFactory firebaseDAOFactory = FirebaseDAOFactory.getInstance();
        collection = firebaseDAOFactory.getCollection("users");
        mAuth = firebaseDAOFactory.getDbAuth();
    }

    @Override
    public void addUser(User user, OnUserAddedListener onUserAddedListener) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                collection.add(user);
                                onUserAddedListener.onUserAdded();
                                } else {
                                onUserAddedListener.onUserAddFailed();
                            }
                        });
    }

    @Override
    public void updateUser(String field, Object object) {
        collection
                .whereEqualTo("email", Objects.requireNonNull(mAuth.getCurrentUser()).getEmail())
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
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                onUserAuthenticatedListener.onUserAuthenticated();
                            } else {
                                onUserAuthenticatedListener.onUserAuthenticatedFailed();
                            }
                        });
    }

    @Override
    public void logOutUser() {
        FirebaseAuth.getInstance().signOut();
    }
}
