package com.example.behealthy;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.behealthy.auth.AuthorizationPageActivity;
import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.model.User;
import com.example.behealthy.util.ViewManager;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPageActivity extends AppCompatActivity {
    @BindView(R.id.diagnosis)
    TextView userDiagnosisTextView;
    @BindView(R.id.temperature_value_text_view)
    TextView temperatureTextView;
    @BindView(R.id.pressure_value_text_view)
    TextView pressureTextView;
    @BindView(R.id.pulse_value_text_view)
    TextView pulseTextView;
    @BindView(R.id.sugar_level_value_text_view)
    TextView sugarLevelTextView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.temperature_block_imageView)
    ImageView temperatureIndexImageView;

    @BindView(R.id.pressure_block_imageView)
    ImageView pressureIndexImageView;

    @BindView(R.id.pulse_block_imageView)
    ImageView pulseIndexImageView;

    @BindView(R.id.sugar_level_block_imageView)
    ImageView sugarLevelIndexImageView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private User currentUser;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private UserDAO fireBaseUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);
        DAOFactory fireStoreDAOFactory = DAOFactory.getDAOFactory(DAOFactory.FIREBASEDAOFACTORY);
        fireBaseUserDAO = fireStoreDAOFactory.getUserDAO();

        setUpToolBar();
        setUpNavigationView();
        setUpHealthTable();
    }

    private void setUpNavigationView() {
        NavigationView navView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.show_indicators_history:
                    moveToActivity(IndicatorsHistoryActivity.class, "INDICATORS_LIST", (Serializable) currentUser.getHealthIndicatorsList());
                    break;
                case R.id.create_new_diagnosis:
                    moveToActivity(DiagnosisActivity.class, "currentUser", currentUser);
                    break;
                case R.id.create_new_health_indicator:
                    moveToActivity(IndicatorsActivity.class, "currentUser", currentUser);
                    break;
                case R.id.create_new_med_taking:
                    moveToActivity(MedTakingActivity.class);
                    break;
                case R.id.doctor_visit:
                    moveToActivity(DoctorVisitActivity.class);
                    break;
                case R.id.graph:
                    moveToActivity(GraphActivity.class, "INDICATORS", (Serializable) currentUser.getHealthIndicatorsList());
                    break;
                case R.id.exit:
                    fireBaseUserDAO.logOutUser();
                    moveToActivity(AuthorizationPageActivity.class);
                    break;
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void moveToActivity(Class<?> toClass, String name, Serializable extra) {
        Intent intent = new Intent(this, toClass);
        intent.putExtra(name, extra);
        startActivity(intent);
    }

    private void moveToActivity(Class<?> toClass) {
        Intent intent = new Intent(this, toClass);
        startActivity(intent);
    }

    private void setUpHealthTable() {
        Intent intent = getIntent();
        fireBaseUserDAO.getUserByEmail(intent.getStringExtra("USER_EMAIL"), user -> {
            currentUser = user;

            ViewManager.setContent(userDiagnosisTextView, currentUser.getDiagnosis());

            if (!currentUser.getHealthIndicatorsList().isEmpty()) {
                double currentTemperature = currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getTemperature();
                ViewManager.setContent(temperatureTextView, String.format("%s °C", currentTemperature));
                defineTemperatureIndex(currentTemperature);

                int currentPressure = currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPressure();
                ViewManager.setContent(pressureTextView, String.format("%s мм.рт.ст", currentPressure));
                definePressureIndex(currentPressure);

                int currentPulse = currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPulse();
                ViewManager.setContent(pulseTextView, String.format("%s ударів/хв", currentPulse));
                definePulseIndex(currentPulse);

                double currentSugarLevel = currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getSugarLevel();
                ViewManager.setContent(sugarLevelTextView, String.format("%s ммоль/л", currentSugarLevel));
                defineSugarLevelIndex(currentSugarLevel);
            }
        });

    }

    private void defineTemperatureIndex(double currentTemperature){
        if (currentTemperature >= 35.6 && currentTemperature <= 36.9) {
            temperatureIndexImageView.setImageResource(R.drawable.very_satisfied);
        } else if (currentTemperature >= 37 && currentTemperature <= 37.9) {
            temperatureIndexImageView.setImageResource(R.drawable.sentiment_neutral);
        } else {
            temperatureIndexImageView.setImageResource(R.drawable.sentiment_dissatisfied);
        }
    }

    private void definePressureIndex(double currentPressure){
        if (currentPressure >= 120 && currentPressure <= 129) {
            pressureIndexImageView.setImageResource(R.drawable.very_satisfied);
        } else if (currentPressure >= 130 && currentPressure <= 139) {
            pressureIndexImageView.setImageResource(R.drawable.sentiment_neutral);
        } else {
            pressureIndexImageView.setImageResource(R.drawable.sentiment_dissatisfied);
        }
    }

    private void definePulseIndex(double currentPulse){
        if (currentPulse >= 50 && currentPulse <= 90) {
            pulseIndexImageView.setImageResource(R.drawable.very_satisfied);
        } else if (currentPulse >= 91 && currentPulse <= 110) {
            pulseIndexImageView.setImageResource(R.drawable.sentiment_neutral);
        } else {
            pulseIndexImageView.setImageResource(R.drawable.sentiment_dissatisfied);
        }
    }

    private void defineSugarLevelIndex(double currentSugarLevel){
        if (currentSugarLevel < 5.6) {
            sugarLevelIndexImageView.setImageResource(R.drawable.very_satisfied);
        } else if (currentSugarLevel >= 5.6 && currentSugarLevel <= 6.9) {
            sugarLevelIndexImageView.setImageResource(R.drawable.sentiment_neutral);
        } else {
            sugarLevelIndexImageView.setImageResource(R.drawable.sentiment_dissatisfied);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpHealthTable();
    }
}
