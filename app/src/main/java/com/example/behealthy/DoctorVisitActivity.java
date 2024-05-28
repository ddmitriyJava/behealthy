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

import com.example.behealthy.service.VisitReminderService;
import com.example.behealthy.util.ViewManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorVisitActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.doctor_pib_edit_text)
    EditText doctorPibEditText;
    @BindView(R.id.visit_date_edit_text)
    EditText visitDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
    }

    @OnClick(R.id.accept)
    void onAcceptButtonClicked() {
        handleDoctorVisit();
    }

    private void handleDoctorVisit() {
        String doctorName = ViewManager.getContent(doctorPibEditText);
        String dateTime = ViewManager.getContent(visitDateEditText);
        createNewDoctorVisit(doctorName, dateTime);
    }

    private void createNewDoctorVisit(String doctorName, String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(dateTime);
            long timeInMillis = date.getTime();
            setVisitReminder(doctorName, timeInMillis);
            Toast.makeText(getApplicationContext(), "Нагадування додано", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Невірний формат дати і часу", Toast.LENGTH_SHORT).show();
        }
    }

    private void setVisitReminder(String doctorName, long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, VisitReminderService.class);
        intent.putExtra("DOCTOR_NAME", doctorName);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
