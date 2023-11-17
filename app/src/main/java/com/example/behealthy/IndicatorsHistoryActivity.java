package com.example.behealthy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.behealthy.entities.HealthIndicators;
import java.util.List;

public class IndicatorsHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicators_list);

        Intent intent = getIntent();
        List<HealthIndicators> indicatorsList = (List<HealthIndicators>) intent.getSerializableExtra("INDICATORS_LIST");
        ListView indicatorsHistoryListView = findViewById(R.id.indicators_list);

        ArrayAdapter<HealthIndicators> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, indicatorsList);
        indicatorsHistoryListView.setAdapter(listAdapter);
    }
}
