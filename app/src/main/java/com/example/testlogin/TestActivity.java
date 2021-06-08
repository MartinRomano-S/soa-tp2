package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    RadioGroup grupoRespuestaTos;
    RadioButton respuestaTos;
    Button botonEnviarTest;
    private boolean tos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        grupoRespuestaTos = findViewById(R.id.radioGroupTos);
        botonEnviarTest = findViewById(R.id.testConsultar);

        botonEnviarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = grupoRespuestaTos.getCheckedRadioButtonId();
                respuestaTos = findViewById(radioId);
                Toast.makeText(TestActivity.this, "Tos: "+convertToBool(respuestaTos.getText()), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean convertToBool(CharSequence respuesta){
        if ("SI".equals(respuesta)) {
            return true;
        } else if ("NO".equals(respuesta)) {
            return false;
        }
        return false;
    }
}