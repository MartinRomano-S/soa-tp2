package com.example.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testlogin.adapters.EmergencyContactsListAdapter;
import com.example.testlogin.interfaces.Inputable;
import com.example.testlogin.models.EmergencyContact;
import com.example.testlogin.utils.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmergencyContactsActivity extends AppCompatActivity implements Inputable {

    RecyclerView listEmergencyContacts;
    EditText txtECName;
    EditText txtECNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        txtECName = findViewById(R.id.txtECName);
        txtECNumber = findViewById(R.id.txtECNumber);
        listEmergencyContacts = findViewById(R.id.listEmergencyContacts);
        addTextChangedListeners();
        listEmergencyContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        listEmergencyContacts.addItemDecoration(divider);
        List<EmergencyContact> list = new ArrayList<>();

        list.add(new EmergencyContact(1, "Martin", 12345678));
        list.add(new EmergencyContact(2, "Pablo", 87654321));
        list.add(new EmergencyContact(3, "Julio", 12365487));
        list.add(new EmergencyContact(4, "Messi", 12365478));
        list.add(new EmergencyContact(5, "Martin", 11111111));
        list.add(new EmergencyContact(2, "Gonzalo", 22222222));
        list.add(new EmergencyContact(3, "Jose", 33333333));
        list.add(new EmergencyContact(4, "Maradona", 44444444));
        list.add(new EmergencyContact(12, "Pablo", 87654321));
        list.add(new EmergencyContact(13, "Julio", 12365487));
        list.add(new EmergencyContact(14, "Messi", 12365478));
        list.add(new EmergencyContact(12, "Pablo", 87654321));
        list.add(new EmergencyContact(13, "Julio", 12365487));
        list.add(new EmergencyContact(14, "Messi", 12365478));

        EmergencyContactsListAdapter ecAdapter = new EmergencyContactsListAdapter(list);
        listEmergencyContacts.setAdapter(ecAdapter);
    }

    @Override
    public void addTextChangedListeners() {
        txtECName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String txtValue = txtECName.getText().toString();

                if(txtValue.length() == 0)
                    txtECName.setError(getString(R.string.errorEmptyField));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        txtECNumber.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                String txtValue = txtECNumber.getText().toString();

                if(txtValue.length() >= 2) {
                    String numPrefix = txtValue.substring(0, 2);

                    if(!numPrefix.equals("15") && !numPrefix.equals("11"))
                        txtECNumber.setError(getString(R.string.errorPhoneNumberFormat));
                }

                if(txtValue.length() != 10)
                    txtECNumber.setError(getString(R.string.errorPhoneNumberFormat));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}
