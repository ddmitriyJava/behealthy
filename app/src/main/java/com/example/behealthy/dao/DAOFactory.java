package com.example.behealthy.dao;

public abstract class DAOFactory {
    public static final int FIREBASEDAOFACTORY = 1;
    public abstract UserDAO getUserDAO();
    public abstract DiagnosisDAO getDiagnosisDAO();

    public static DAOFactory getDAOFactory(int whichDAOFactory){
        switch (whichDAOFactory){
            case FIREBASEDAOFACTORY:
                return FirebaseDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
