package com.example.behealthy.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.behealthy.MainPageActivity;
import com.example.behealthy.R;
import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.dao.util.OnUserAddedListener;
import com.example.behealthy.entities.User;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment {
    @BindView(R.id.user_full_name_edit_text)
    EditText userPIBEditText;
    @BindView(R.id.user_email_edit_text)
    EditText userEmailEditText;
    @BindView(R.id.user_password_edit_text)
    EditText userPasswordEditText;
    @BindView(R.id.sign_up_button)
    Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        FirebaseApp.initializeApp(requireContext());
        ButterKnife.bind(this, fView);

        signUpButton.setOnClickListener(view -> onSignUpButtonClick(userPIBEditText.getText().toString(), userEmailEditText.getText().toString(),
                userPasswordEditText.getText().toString()));

        return fView;
    }

    public void onSignUpButtonClick(String userPIB, String userEmail, String userPassword) {
        User user = new User(userPIB, userEmail, userPassword, "", new ArrayList<>());

        DAOFactory fireStoreDAOFactory = DAOFactory.getDAOFactory(DAOFactory.FIRESTOREDAOFACTORY);
        UserDAO fireStoreUserDAO = fireStoreDAOFactory.getUserDAO();

        fireStoreUserDAO.addUser(user, new OnUserAddedListener() {
            @Override
            public void onUserAdded() {
                moveToMainPage(userEmail);
            }

            @Override
            public void onUserAddFailed() {
                Toast.makeText(requireContext(), "Перевірте правильність вводу даних", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToMainPage(String userEmail) {
        Intent intent = new Intent(requireContext(), MainPageActivity.class);
        intent.putExtra("USER_EMAIL", userEmail);
        startActivity(intent);
    }
}