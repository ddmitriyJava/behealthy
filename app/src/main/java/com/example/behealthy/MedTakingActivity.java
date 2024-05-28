package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.behealthy.service.MedicationReminderService;
import com.example.behealthy.util.ViewManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedTakingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.med_name_edit_text)
    EditText medNameEditText;
    @BindView(R.id.taking_date_edit_text)
    EditText takingDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_taking);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
    }

    @OnClick(R.id.accept)
    void onAcceptButtonClicked() {
        handleCreateNewMedTaking();
    }

    private void handleCreateNewMedTaking() {
        String medicationName = ViewManager.getContent(medNameEditText);
        String dateTime = ViewManager.getContent(takingDateEditText);
        createNewMedTaking(medicationName, dateTime);
    }

    private void createNewMedTaking(String medicationName, String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        try {
            Date date = dateFormat.parse(dateTime);
            assert date != null;
            long timeInMillis = date.getTime();
            setMedicationReminder(medicationName, timeInMillis);
            Toast.makeText(getApplicationContext(), "Нагадування додано", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Невірний формат дати та часу", Toast.LENGTH_SHORT).show();
        }
    }

    private void setMedicationReminder(String medicationName, long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MedicationReminderService.class);
        intent.putExtra("MEDICATION_NAME", medicationName);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}