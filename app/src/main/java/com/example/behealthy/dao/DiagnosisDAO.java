package com.example.behealthy.dao;

import com.example.behealthy.dao.util.OnDiagnosisLoadedListener;
import com.example.behealthy.dao.util.OnUserLoadedListener;
import com.example.behealthy.model.Diagnosis;

public interface DiagnosisDAO {
    void getDiagnosis(String name, OnDiagnosisLoadedListener diagnosisLoadedListener);
}
