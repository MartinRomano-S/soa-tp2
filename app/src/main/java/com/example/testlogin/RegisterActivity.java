package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.services.LoginService;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements Asyncronable {

    Button btnCancel;
    Button btnRegister;
    ProgressBar pgbRegister;
    EditText txtName;
    EditText txtLastname;
    EditText txtDNI;
    EditText txtEmail;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);
        pgbRegister = findViewById(R.id.pgbRegister);
        txtName = findViewById(R.id.txtName);
        txtLastname = findViewById(R.id.txtLastname);
        txtDNI = findViewById(R.id.txtDNI);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject json = new JSONObject();

                try {
                    json.put("env", "TEST");
                    json.put("name", txtName.getText().toString());
                    json.put("lastname", txtLastname.getText().toString());
                    json.put("dni", Integer.valueOf(txtDNI.getText().toString()));
                    json.put("email", txtEmail.getText().toString());
                    json.put("password", txtPassword.getText().toString());
                    json.put("commission", 3900);
                    json.put("group", 4);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Error en el JSON", Toast.LENGTH_SHORT).show();
                }
                LoginService ls = new LoginService(RegisterActivity.this, RegisterActivity.this, R.string.api_register_url, SOAAPIallowedMethodsEnum.POST, json);
                ls.execute();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity(0);
            }
        });
    }

    @Override
    public void toggleProgressBar(boolean status) {
        if (status)
            pgbRegister.setVisibility(View.VISIBLE);
        else
            pgbRegister.setVisibility(View.GONE);
    }
}
