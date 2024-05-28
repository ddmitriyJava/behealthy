package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.behealthy.dao.DAOFactory;
import com.example.behealthy.dao.DiagnosisDAO;
import com.example.behealthy.dao.UserDAO;
import com.example.behealthy.model.User;
import com.example.behealthy.util.ViewManager;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiagnosisActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.diagnosis)
    EditText userDiagnosisEditText;

    @BindView(R.id.information)
    ScrollView scrollView;

    @BindView(R.id.diagnosis_name)
    TextView diagnosisNameTextView;

    @BindView(R.id.text)
    TextView textTextView;

    private User currentUser;
    private UserDAO fireBaseUserDAO;
    private DiagnosisDAO fireBaseDiagnosisDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);

        scrollView.setVisibility(View.GONE);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        userDiagnosisEditText.setText(currentUser.getDiagnosis());
        DAOFactory fireStoreDAOFactory = DAOFactory.getDAOFactory(DAOFactory.FIREBASEDAOFACTORY);
        fireBaseUserDAO = fireStoreDAOFactory.getUserDAO();
        fireBaseDiagnosisDAO = fireStoreDAOFactory.getDiagnosisDAO();

    }

    @OnClick(R.id.accept)
    void onAcceptButtonClicked(){
        String userDiagnosis = ViewManager.getContent(userDiagnosisEditText);
        currentUser.setDiagnosis(userDiagnosis);
        fireBaseUserDAO.updateUser("diagnosis", userDiagnosis);
        Toast.makeText(getApplicationContext(), "Новий діагноз збережено", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.show_information)
    void onShowInformationButtonClicked(){
        String diagnosisName = currentUser.getDiagnosis();

        fireBaseDiagnosisDAO.getDiagnosis(diagnosisName, diagnosis -> {
            if (diagnosis != null) {
                String diagnosisDescription = diagnosis.getDescription();
                diagnosisNameTextView.setText(diagnosisName);
                textTextView.setText(diagnosisDescription);
                scrollView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Помилка", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}