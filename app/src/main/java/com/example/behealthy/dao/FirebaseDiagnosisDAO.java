package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnDiagnosisLoadedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.model.Diagnosis;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicReference;

public class FirebaseDiagnosisDAO implements DiagnosisDAO{

    private CollectionReference collection;

    public FirebaseDiagnosisDAO() {
        FirebaseDAOFactory firebaseDAOFactory = FirebaseDAOFactory.getInstance();
        collection = firebaseDAOFactory.getCollection("diagnosis");
    }

    @Override
    public void getDiagnosis(String name, OnDiagnosisLoadedListener diagnosisLoadedListener) {
        collection
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String description = document.getString("information");
                            Diagnosis diagnosis = new Diagnosis(name, description);
                            diagnosisLoadedListener.onDiagnosisLoaded(diagnosis);
                            return; // Return after finding the diagnosis
                        }
                    }
                    // If the diagnosis is not found, notify the listener with null
                    diagnosisLoadedListener.onDiagnosisLoaded(null);
                });
    }
}
