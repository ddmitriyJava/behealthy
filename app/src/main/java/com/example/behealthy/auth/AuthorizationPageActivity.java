package com.example.behealthy.auth;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.TextView;

import com.example.behealthy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorizationPageActivity extends AppCompatActivity {
    @BindView(R.id.sign_up_text_view)
    TextView signUpTextView;
    @BindView(R.id.sign_in_text_view)
    TextView signInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_page);

        ButterKnife.bind(this);
        setUp();
    }

    private void setUp(){
        changeFragment(new SignUpFragment());
        changeTextAppearance(signUpTextView, getStyleRes(true));
        changeTextAppearance(signInTextView, getStyleRes(false));
    }

    @OnClick(R.id.sign_up_text_view)
    void onSignUpClick(){
        changeFragmentAndSetAppearance(signUpTextView, signInTextView, new SignUpFragment(), getStyleRes(true));
    }

    @OnClick(R.id.sign_in_text_view)
    void onSignInClick(){
        changeFragmentAndSetAppearance(signInTextView, signUpTextView, new SignInFragment(), getStyleRes(true));
    }

    private void changeFragmentAndSetAppearance(TextView selectedTextView, TextView unselectedTextView, Fragment fragment, @StyleRes int id){
        changeTextAppearance(unselectedTextView, getStyleRes(false));
        changeTextAppearance(selectedTextView, id);
        changeFragment(fragment);
    }

    private void changeTextAppearance(TextView textView, @StyleRes int id) {
        textView.setTextAppearance(id);
    }

    private @StyleRes int getStyleRes(boolean selected) {
        return selected ? R.style.selected_text_view_style : R.style.inactive_text_view_style;
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}