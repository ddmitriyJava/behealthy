package com.example.behealthy.dao;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreDAOFactory extends DAOFactory{
    private FirebaseFirestore db;
    private static FireStoreDAOFactory fireStoreDAOFactory;

    private FireStoreDAOFactory(){
        db = FirebaseFirestore.getInstance();
    }

    public CollectionReference getCollection(){
        return db.collection("users");
    }

    public static FireStoreDAOFactory getInstance(){
        if(fireStoreDAOFactory == null){
            fireStoreDAOFactory = new FireStoreDAOFactory();
        }

        return fireStoreDAOFactory;
    }

    public UserDAO getUserDAO(){
        return new FireStoreUserDAO();
    }
}
