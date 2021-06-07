package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.services.LoginService;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Asyncronable {

    Button btnLogin;
    Button btnRegister;
    ProgressBar prgLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        prgLogin = findViewById(R.id.pgbRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            JSONObject json = new JSONObject();
            try {
                json.put("email", "martinpromano@gmail.com");
                json.put("password", "prueba123");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoginService ls = new LoginService(LoginActivity.this, LoginActivity.this, R.string.api_login_url, SOAAPIallowedMethodsEnum.POST, json);
            ls.execute();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void toggleProgressBar(boolean status) {
        if (status)
            prgLogin.setVisibility(View.VISIBLE);
        else
            prgLogin.setVisibility(View.GONE);
    }
}
