package com.example.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnGoToTest;
    Button btnGoToEmergencyContacts;
    Button btnGoToEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGoToTest = findViewById(R.id.btnGoToTest);
        btnGoToEmergencyContacts = findViewById(R.id.btnGoToEmergencyContacts);
        btnGoToEventList = findViewById(R.id.btnGoToEventList);

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
    }
}
