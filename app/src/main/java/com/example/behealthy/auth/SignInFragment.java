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

import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.dao.util.OnUserAuthenticatedListener;
import com.example.behealthy.util.EditTextListener;
import com.example.behealthy.MainPageActivity;
import com.example.behealthy.R;
import com.example.behealthy.util.ViewManager;
import com.google.firebase.FirebaseApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInFragment extends Fragment {
    @BindView(R.id.sign_in_user_email_edit_text)
    EditText userEmailEditText;
    @BindView(R.id.sign_in_user_password_edit_text)
    EditText userPasswordEditText;
    @BindView(R.id.sign_in_button)
    Button signInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        FirebaseApp.initializeApp(requireContext());
        ButterKnife.bind(this, fragmentView);
        setListeners();
        return fragmentView;
    }

    private void setListeners() {
        userEmailEditText.addTextChangedListener(new EditTextListener(userEmailEditText));
        userPasswordEditText.addTextChangedListener(new EditTextListener(userPasswordEditText));
        signInButton.setOnClickListener(view -> onSignInButtonClick(getUserEmail(), getUserPassword()));
    }

    public void onSignInButtonClick(String userEmail, String userPassword) {
        DAOFactory fireStoreDAOFactory = DAOFactory.getDAOFactory(DAOFactory.FIREBASEDAOFACTORY);
        UserDAO fireStoreUserDAO = fireStoreDAOFactory.getUserDAO();

        if (isAdded()) {
            fireStoreUserDAO.authenticateUser(userEmail, userPassword, new OnUserAuthenticatedListener() {
                @Override
                public void onUserAuthenticated() {
                    moveToMainPage();
                }

                @Override
                public void onUserAuthenticatedFailed() {
                    Toast.makeText(requireContext(), "Неправильна пошта або пароль", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getUserEmail() {
        return ViewManager.getContent(userEmailEditText);
    }

    public String getUserPassword() {
        return ViewManager.getContent(userPasswordEditText);
    }

    private void moveToMainPage() {
        Intent intent = new Intent(requireContext(), MainPageActivity.class);
        intent.putExtra("USER_EMAIL", getUserEmail());
        startActivity(intent);
    }
}
