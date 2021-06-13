package com.example.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testlogin.utils.SharedPreferencesManager;

public class HomeActivity extends AppCompatActivity {

    Button btnGoToTest;
    Button btnGoToEmergencyContacts;
    Button btnGoToEventList;
    Button btnGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGoToTest = findViewById(R.id.btnGoToTest);
        btnGoToEmergencyContacts = findViewById(R.id.btnGoToEmergencyContacts);
        btnGoToEventList = findViewById(R.id.btnGoToEventList);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        btnGoToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TestActivity.class));
            }
        });

        btnGoToEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, EmergencyContactsActivity.class));
            }
        });

        btnGoToEventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, EventsActivity.class));
            }
        });

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferencesManager spm = SharedPreferencesManager.getInstance(HomeActivity.this);
                spm.delete();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        });
    }
}
