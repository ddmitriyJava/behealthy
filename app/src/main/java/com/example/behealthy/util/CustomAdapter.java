package com.example.behealthy.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.behealthy.R;
import com.example.behealthy.model.HealthIndicators;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<HealthIndicators> {
    private final Context context;
    private final List<HealthIndicators> healthIndicatorsList;

    public CustomAdapter(@NonNull Context context, List<HealthIndicators> healthIndicatorsList) {
        super(context, 0, healthIndicatorsList);

        this.context = context;
        this.healthIndicatorsList = healthIndicatorsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.indicators_list_item, parent, false);
        }

        HealthIndicators healthIndicators = healthIndicatorsList.get(position);

        TextView temperatureValue = listItem.findViewById(R.id.temperature_value);
        temperatureValue.setText(String.format("%s °C", healthIndicators.getTemperature()));

        TextView pressureValue = listItem.findViewById(R.id.pressure_value);
        pressureValue.setText(String.format("%s мм.рт.ст", healthIndicators.getPressure()));

        TextView pulseValue = listItem.findViewById(R.id.pulse_value);
        pulseValue.setText(String.format("%s ударів/хв", healthIndicators.getPulse()));

        TextView sugarLevelValue = listItem.findViewById(R.id.sugar_level_value);
        sugarLevelValue.setText(String.format("%s ммоль/л", healthIndicators.getSugarLevel()));

        return listItem;
    }
}
