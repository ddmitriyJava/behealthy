package com.example.behealthy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.behealthy.entities.HealthIndicators;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphActivity extends AppCompatActivity {
    private LineChart temperatureChart;
    private LineChart pressureChart;
    private LineChart pulseChart;
    private LineChart sugarLevelChart;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.graphs);

        init();

        Intent intent = getIntent();
        List<HealthIndicators> healthIndicatorsList = (List<HealthIndicators>) intent.getSerializableExtra("INDICATORS");
        List<Double> temperatureList = healthIndicatorsList.stream().map(HealthIndicators::getTemperature).collect(Collectors.toList());
        List<Double> pressureList = healthIndicatorsList.stream().map(HealthIndicators::getPressure).collect(Collectors.toList());
        List<Double> pulseList = healthIndicatorsList.stream().map(HealthIndicators::getPulse).collect(Collectors.toList());
        List<Double> sugarLevelList = healthIndicatorsList.stream().map(HealthIndicators::getSugarLevel).collect(Collectors.toList());


        ArrayList<Entry> temperatureEntries = new ArrayList<>();
        for (int i = 0; i < temperatureList.size(); i++) {
            double value = temperatureList.get(i);
            temperatureEntries.add(new Entry(i, (float) value));
        }

        ArrayList<Entry> pressureEntries = new ArrayList<>();
        for (int i = 0; i < pressureList.size(); i++) {
            double value = pressureList.get(i);
            pressureEntries.add(new Entry(i, (float) value));
        }

        ArrayList<Entry> pulseEntries = new ArrayList<>();
        for (int i = 0; i < pulseList.size(); i++) {
            double value = pulseList.get(i);
            pulseEntries.add(new Entry(i, (float) value));
        }

        ArrayList<Entry> sugarLevelEntries = new ArrayList<>();
        for (int i = 0; i < sugarLevelList.size(); i++) {
            double value = sugarLevelList.get(i);
            sugarLevelEntries.add(new Entry(i, (float) value));
        }

        LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "ТЕМПЕРАТУРА");
        temperatureDataSet.setColor(Color.BLUE);
        temperatureDataSet.setValueTextColor(Color.BLACK);

        LineDataSet pressureDataSet = new LineDataSet(pressureEntries, "ТИСК");
        pressureDataSet.setColor(Color.BLUE);
        pressureDataSet.setValueTextColor(Color.BLACK);

        LineDataSet pulseDataSet = new LineDataSet(pulseEntries, "ПУЛЬС");
        pulseDataSet.setColor(Color.BLUE);
        pulseDataSet.setValueTextColor(Color.BLACK);

        LineDataSet sugarLevelDataSet = new LineDataSet(sugarLevelEntries, "РІВЕНЬ ЦУКРУ");
        sugarLevelDataSet.setColor(Color.BLUE);
        sugarLevelDataSet.setValueTextColor(Color.BLACK);

        LineData temperatureLineData = new LineData(temperatureDataSet);
        LineData pressureLineData = new LineData(pressureDataSet);
        LineData pulseLineData = new LineData(pulseDataSet);
        LineData sugarLevelLineData = new LineData(sugarLevelDataSet);

        temperatureChart.setData(temperatureLineData);
        pressureChart.setData(pressureLineData);
        pulseChart.setData(pulseLineData);
        sugarLevelChart.setData(sugarLevelLineData);

        // Настройте дополнительные параметры графика, например, масштабирование, подписи и т. д.
        temperatureChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        pressureChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        pulseChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        sugarLevelChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // Обновите график
        temperatureChart.invalidate();
        pressureChart.invalidate();
        pulseChart.invalidate();
        sugarLevelChart.invalidate();
    }

    private void init(){
        temperatureChart = findViewById(R.id.temperature_chart);
        pressureChart = findViewById(R.id.pressure_chart);
        pulseChart = findViewById(R.id.pulse_chart);
        sugarLevelChart = findViewById(R.id.sugar_level_chart);
    }
}
