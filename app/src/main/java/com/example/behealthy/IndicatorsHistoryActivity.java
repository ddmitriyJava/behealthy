package com.example.behealthy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.behealthy.model.HealthIndicators;
import com.example.behealthy.util.CustomAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndicatorsHistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators_list);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        Intent intent = getIntent();
        List<HealthIndicators> indicatorsList = (List<HealthIndicators>) intent.getSerializableExtra("INDICATORS_LIST");
        ListView indicatorsHistoryListView = findViewById(R.id.indicators_list);

        CustomAdapter customAdapter = new CustomAdapter(this, indicatorsList);
        indicatorsHistoryListView.setAdapter(customAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
