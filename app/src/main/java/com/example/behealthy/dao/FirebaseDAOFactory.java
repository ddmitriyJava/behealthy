package com.example.behealthy.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDAOFactory extends DAOFactory{
    private FirebaseFirestore db;
    private FirebaseAuth dbAuth;
    private static FirebaseDAOFactory firebaseDAOFactory;

    private FirebaseDAOFactory(){
        db = FirebaseFirestore.getInstance();
        dbAuth = FirebaseAuth.getInstance();
    }

    public CollectionReference getCollection(String collection){
        return db.collection(collection);
    }
    public FirebaseAuth getDbAuth(){return dbAuth;}

    public static FirebaseDAOFactory getInstance(){
        if(firebaseDAOFactory == null){
            firebaseDAOFactory = new FirebaseDAOFactory();
        }

        return firebaseDAOFactory;
    }

    public UserDAO getUserDAO(){
        return new FirebaseUserDAO();
    }

    @Override
    public DiagnosisDAO getDiagnosisDAO() {
        return new FirebaseDiagnosisDAO();
    }
}
