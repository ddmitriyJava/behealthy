package com.example.behealthy;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.entities.HealthIndicators;
import com.example.behealthy.entities.User;
import com.example.behealthy.service.MedicationReminderService;
import com.example.behealthy.service.VisitReminderService;
import com.example.behealthy.util.ViewManager;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainPageActivity extends AppCompatActivity {
    @BindView(R.id.greeting_text_view)
    TextView greetingTextView;
    @BindView(R.id.diagnosis_text_view)
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
    private User currentUser;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private UserDAO fireStoreUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);
        DAOFactory fireStoreDAOFactory = DAOFactory.getDAOFactory(DAOFactory.FIRESTOREDAOFACTORY);
        fireStoreUserDAO = fireStoreDAOFactory.getUserDAO();

        setUpToolBar();
        setUpNavigationView();
        setUp();
    }

    private void setUpNavigationView() {
        NavigationView navView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.create_new_health_indicator:
                    handleCreateNewHealthIndicator();
                    break;
                case R.id.create_new_med_taking:
                    handleCreateNewMedTaking();
                    break;
                case R.id.doctor_visit:
                    handleDoctorVisit();
                    break;
                case R.id.graph:
                    handleGraph();
                    break;
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void handleGraph() {
        moveToActivity(GraphActivity.class, "INDICATORS", (Serializable) currentUser.getHealthIndicatorsList());
    }

    private void handleDoctorVisit() {
        View dialogView = getLayoutInflater().inflate(R.layout.add_new_visit_dialog, null);
        EditText doctorNameEditText = dialogView.findViewById(R.id.doctor_name_edit_text);
        EditText dateTimeEditText = dialogView.findViewById(R.id.date_time_edit_text);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Зберегти", (dialog, which) -> {
                    String doctorName = ViewManager.getContent(doctorNameEditText);
                    String dateTime = ViewManager.getContent(dateTimeEditText);
                    createNewDoctorVisit(doctorName, dateTime);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Нагадування додано", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Скасувати", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void createNewDoctorVisit(String doctorName, String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateTime);
            long timeInMillis = date.getTime();
            setVisitReminder(doctorName, timeInMillis);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Невірний формат дати і часу", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCreateNewMedTaking() {
        View dialogView = getLayoutInflater().inflate(R.layout.add_new_med_taking_dialog, null);
        EditText medicationNameEditText = dialogView.findViewById(R.id.med_name_edit_text);
        EditText dateTimeEditText = dialogView.findViewById(R.id.date_time_edit_text);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Зберегти", (dialog, which) -> {
                    String medicationName = ViewManager.getContent(medicationNameEditText);
                    String dateTime = ViewManager.getContent(dateTimeEditText);
                    createNewMedTaking(medicationName, dateTime);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Нагадування додано", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Скасувати", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void createNewMedTaking(String medicationName, String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateTime);
            long timeInMillis = date.getTime();
            setMedicationReminder(medicationName, timeInMillis);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Невірний формат дати та часу", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCreateNewHealthIndicator() {
        View dialogView = getLayoutInflater().inflate(R.layout.add_indicators_dialog, null);

        EditText temperatureEditText = dialogView.findViewById(R.id.temperature_edit_text);
        EditText pressureEditText = dialogView.findViewById(R.id.pressure_edit_text);
        EditText pulseEditText = dialogView.findViewById(R.id.pulse_edit_text);
        EditText bloodSugarLevelEditText = dialogView.findViewById(R.id.blood_sugar_level_edit_text);

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Додати", (dialog, which) -> addNewHealthIndicator(ViewManager.getContent(temperatureEditText), ViewManager.getContent(pressureEditText),
                        ViewManager.getContent(pulseEditText), ViewManager.getContent(bloodSugarLevelEditText)))
                .setNegativeButton("Скасувати", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void addNewHealthIndicator(String temperature, String pressure, String pulse, String bloodSugarLevel) {
        try {
            List<HealthIndicators> newHealthIndicatorsList = new ArrayList<>(currentUser.getHealthIndicatorsList());
            newHealthIndicatorsList.add(new HealthIndicators(Double.parseDouble(temperature), Double.parseDouble(pressure),
                    Double.parseDouble(pulse), Double.parseDouble(bloodSugarLevel)));
            currentUser.setHealthIndicatorsList(newHealthIndicatorsList);

            fireStoreUserDAO.updateUser(currentUser.getEmail(), "healthIndicatorsList", newHealthIndicatorsList);

            ViewManager.setContent(temperatureTextView, String.format("%s °C", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getTemperature()));
            ViewManager.setContent(pressureTextView, String.format("%s мм.рт.ст", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPressure()));
            ViewManager.setContent(pulseTextView, String.format("%s ударів/хв", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPulse()));
            ViewManager.setContent(sugarLevelTextView, String.format("%s ммоль/л", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getSugarLevel()));

            Toast.makeText(getApplicationContext(), "Показник додано", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Невірний формат даних", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.health_indicator_list_text_view)
    void showHealthIndicatorsHistory() {
        moveToActivity(IndicatorsHistoryActivity.class, "INDICATORS_LIST", (Serializable) currentUser.getHealthIndicatorsList());
    }

    private void setMedicationReminder(String medicationName, long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MedicationReminderService.class);
        intent.putExtra("MEDICATION_NAME", medicationName);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    private void setVisitReminder(String doctorName, long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, VisitReminderService.class);
        intent.putExtra("DOCTOR_NAME", doctorName);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void moveToActivity(Class<?> toClass, String name, Serializable extra) {
        Intent intent = new Intent(this, toClass);
        intent.putExtra(name, extra);
        startActivity(intent);
    }

    private void setUp() {
        Intent intent = getIntent();
        fireStoreUserDAO.getUserByEmail(intent.getStringExtra("USER_EMAIL"), user -> {
            currentUser = user;

            ViewManager.setContent(greetingTextView, String.format("%s%s%s", ViewManager.getContent(greetingTextView), " ", currentUser.getPib().toUpperCase()));
            ViewManager.setContent(userDiagnosisTextView, String.format("%s%s%s", ViewManager.getContent(userDiagnosisTextView), " ", currentUser.getDiagnosis()));

            if (!currentUser.getHealthIndicatorsList().isEmpty()) {
                ViewManager.setContent(temperatureTextView, String.format("%s °C", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getTemperature()));
                ViewManager.setContent(pressureTextView, String.format("%s мм.рт.ст", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPressure()));
                ViewManager.setContent(pulseTextView, String.format("%s ударів/хв", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getPulse()));
                ViewManager.setContent(sugarLevelTextView, String.format("%s ммоль/л", currentUser.getHealthIndicatorsList().get(currentUser.getHealthIndicatorsList().size() - 1).getSugarLevel()));
            }
        });

    }
}