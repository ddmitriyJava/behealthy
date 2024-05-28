package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.FirebaseDAOFactory;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.model.HealthIndicators;
import com.example.behealthy.model.User;
import com.example.behealthy.util.ViewManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndicatorsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private User currentUser;
    private UserDAO fireBaseUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        DAOFactory daoFactory = FirebaseDAOFactory.getDAOFactory(DAOFactory.FIREBASEDAOFACTORY);
        fireBaseUserDAO = daoFactory.getUserDAO();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        replaceMainBlockFragment();
    }

    private void replaceInfoBlockFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new InfoBlockFragment())
                .commit();
    }

    private void replaceMainBlockFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainBlockFragment())
                .commit();
    }

    @OnClick(R.id.show_information)
    void onShowInformationButtonClicked() {
        replaceInfoBlockFragment();
    }

    @OnClick(R.id.accept)
    void onAcceptButtonClicked() {
        String temperature = ((EditText) Objects.requireNonNull(getSupportFragmentManager()
                        .findFragmentById(R.id.container))
                .requireView()
                .findViewById(R.id.temperature_value))
                .getText()
                .toString();

        String pressure = ((EditText) Objects.requireNonNull(getSupportFragmentManager()
                        .findFragmentById(R.id.container))
                .requireView()
                .findViewById(R.id.pressure_value))
                .getText()
                .toString();

        String pulse = ((EditText) Objects.requireNonNull(getSupportFragmentManager()
                        .findFragmentById(R.id.container))
                .requireView()
                .findViewById(R.id.pulse_value))
                .getText()
                .toString();

        String sugarLevel = ((EditText) Objects.requireNonNull(getSupportFragmentManager()
                        .findFragmentById(R.id.container))
                .requireView()
                .findViewById(R.id.sugar_level_value))
                .getText()
                .toString();

        List<HealthIndicators> newHealthIndicatorsList = new ArrayList<>(currentUser.getHealthIndicatorsList());
        newHealthIndicatorsList.add(new HealthIndicators(Double.parseDouble(temperature), Integer.parseInt(pressure),
                Integer.parseInt(pulse), Double.parseDouble(sugarLevel)));
        currentUser.setHealthIndicatorsList(newHealthIndicatorsList);
        fireBaseUserDAO.updateUser("healthIndicatorsList", newHealthIndicatorsList);

    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}