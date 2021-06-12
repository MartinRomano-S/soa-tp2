package com.example.testlogin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testlogin.adapters.EmergencyContactsListAdapter;
import com.example.testlogin.interfaces.Inputable;
import com.example.testlogin.models.EmergencyContact;
import com.example.testlogin.utils.Configuration;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmergencyContactsActivity extends AppCompatActivity implements Inputable {

    RecyclerView listEmergencyContacts;
    EditText txtECName;
    EditText txtECNumber;
    Button btnAddEmergencyContact;
    List<EmergencyContact> emergencyContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        txtECName = findViewById(R.id.txtECName);
        txtECNumber = findViewById(R.id.txtECNumber);
        listEmergencyContacts = findViewById(R.id.listEmergencyContacts);
        btnAddEmergencyContact = findViewById(R.id.btnAddEmergencyContact);

        addInputChangedListeners();

        listEmergencyContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.divider)));
        listEmergencyContacts.addItemDecoration(divider);
        emergencyContacts = null;
        //Configuration.removeEmergencyContactList(this);

        try {
            emergencyContacts = Configuration.getEmergencyContactList(this);

            if(emergencyContacts.isEmpty())
                emergencyContacts = new ArrayList<>();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        refreshEmergencyContactList(emergencyContacts);

        btnAddEmergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNameValue = txtECName.getText().toString();
                String txtNumberValue = txtECNumber.getText().toString();

                if(txtNumberValue.length() >= 2) {
                    String numPrefix = txtNumberValue.substring(0, 2);

                    if(!numPrefix.equals("15") && !numPrefix.equals("11"))
                        return;
                }

                if(txtNameValue.length() > 0 && txtNumberValue.length() == 10) {
                    EmergencyContact ec = new EmergencyContact();

                    int newId;

                    if(emergencyContacts.isEmpty()) {
                        emergencyContacts = new ArrayList<>();
                        newId = 1;
                    } else
                        //Obtengo el último contacto agregado y calculo el nuevo ID
                        newId = emergencyContacts.get(emergencyContacts.size() - 1).getId() + 1;

                    ec.setId(newId);
                    ec.setName(txtNameValue);
                    ec.setPhoneNumber(Integer.valueOf(txtNumberValue));

                    emergencyContacts.add(ec);

                    Configuration.saveEmergencyContactList(EmergencyContactsActivity.this, emergencyContacts);

                    refreshEmergencyContactList(emergencyContacts);
                }
            }
        });
    }

    public void refreshEmergencyContactList(List<EmergencyContact> list) {

        EmergencyContactsListAdapter ecAdapter = new EmergencyContactsListAdapter(list, EmergencyContactsActivity.this);
        listEmergencyContacts.setAdapter(ecAdapter);
    }

    @Override
    public void addInputChangedListeners() {
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
