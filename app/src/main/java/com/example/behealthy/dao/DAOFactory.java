package com.example.behealthy.dao;

public abstract class DAOFactory {
    public static final int FIRESTOREDAOFACTORY = 1;

    public abstract UserDAO getUserDAO();

    public static DAOFactory getDAOFactory(int whichDAOFactory){
        switch (whichDAOFactory){
            case FIRESTOREDAOFACTORY:
                return FireStoreDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
