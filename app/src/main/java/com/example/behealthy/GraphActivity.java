package com.example.behealthy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.behealthy.model.HealthIndicators;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphActivity extends AppCompatActivity {
    @BindView(R.id.temperature_chart)
    LineChart temperatureChart;
    @BindView(R.id.pressure_chart)
    LineChart pressureChart;
    @BindView(R.id.pulse_chart)
    LineChart pulseChart;
    @BindView(R.id.sugar_level_chart)
    LineChart sugarLevelChart;
    private List<HealthIndicators> healthIndicatorsList;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.graphs);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        healthIndicatorsList = (List<HealthIndicators>) intent.getSerializableExtra("INDICATORS");

        LineDataSet temperatureDataSet = new LineDataSet(initIndicatorEntriesAndGet("temperature"), "ТЕМПЕРАТУРА");
        setDataSet(temperatureDataSet);
        setChart(temperatureChart);

        LineDataSet pressureDataSet = new LineDataSet(initIndicatorEntriesAndGet("pressure"), "ТИСК");
        setDataSet(pressureDataSet);
        setChart(pressureChart);

        LineDataSet pulseDataSet = new LineDataSet(initIndicatorEntriesAndGet("pulse"), "ПУЛЬС");
        setDataSet(pulseDataSet);
        setChart(pulseChart);

        LineDataSet sugarLevelDataSet = new LineDataSet(initIndicatorEntriesAndGet("sugarLevel"), "РІВЕНЬ ЦУКРУ");
        setDataSet(sugarLevelDataSet);
        setChart(sugarLevelChart);

        LineData temperatureLineData = new LineData(temperatureDataSet);
        LineData pressureLineData = new LineData(pressureDataSet);
        LineData pulseLineData = new LineData(pulseDataSet);
        LineData sugarLevelLineData = new LineData(sugarLevelDataSet);

        setData(temperatureLineData, pressureLineData, pulseLineData, sugarLevelLineData);
        invalidate();
    }

    private void setData(LineData temperatureLineData, LineData pressureLineData, LineData pulseLineData, LineData sugarLevelLineData){
        temperatureChart.setData(temperatureLineData);
        pressureChart.setData(pressureLineData);
        pulseChart.setData(pulseLineData);
        sugarLevelChart.setData(sugarLevelLineData);
    }

    private List<Number> getIndicatorList(String indicator){
        if(Objects.equals(indicator, "temperature")){
            return healthIndicatorsList.stream().map(HealthIndicators::getTemperature).collect(Collectors.toList());
        } else if (Objects.equals(indicator, "pressure")) {
            return healthIndicatorsList.stream().map(HealthIndicators::getPressure).collect(Collectors.toList());
        } else if (Objects.equals(indicator, "pulse")) {
            return healthIndicatorsList.stream().map(HealthIndicators::getPulse).collect(Collectors.toList());
        } else {
            return healthIndicatorsList.stream().map(HealthIndicators::getSugarLevel).collect(Collectors.toList());
        }
    }

    private List<Entry> initIndicatorEntriesAndGet(String indicator){
        List<Number> list = getIndicatorList(indicator);
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Number value = list.get(i);
            float floatValue = value.floatValue();
            entries.add(new Entry(i, floatValue));
        }

        return entries;
    }


    private void setChart(LineChart lineChart){
        lineChart.setExtraBottomOffset(10f);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        YAxis leftYAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        leftYAxis.setTextColor(Color.WHITE);
        rightYAxis.setTextColor(Color.WHITE);

        temperatureChart.invalidate();
    }

    private void setDataSet(LineDataSet lineDataSet){
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setDrawValues(true);
    }

    private void invalidate(){
        temperatureChart.invalidate();
        pressureChart.invalidate();
        pulseChart.invalidate();
        sugarLevelChart.invalidate();
    }
}
