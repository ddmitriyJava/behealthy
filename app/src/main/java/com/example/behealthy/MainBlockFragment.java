package com.example.behealthy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainBlockFragment extends Fragment {
    @BindView(R.id.temperature_value)
    EditText temperatureValueEditText;
    @BindView(R.id.pressure_value)
    EditText pressureValueEditText;
    @BindView(R.id.pulse_value)
    EditText pulseValueEditText;
    @BindView(R.id.sugar_level_value)
    EditText sugarLevelValueEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_block, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}