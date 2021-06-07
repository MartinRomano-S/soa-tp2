package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.testlogin.interfaces.AsyncronableRequest;
import com.example.testlogin.services.AsyncRequestService;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements AsyncronableRequest {

    Button btnLogin;
    Button btnToRegister;
    EditText txtUser;
    EditText txtPasswordLogin;
    ProgressBar prgLogin;

    /* COMENTARIO DE PRUEBA */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);
        txtUser = findViewById(R.id.txtUser);
        txtPasswordLogin = findViewById(R.id.txtPasswordLogin);
        prgLogin = findViewById(R.id.pgbLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            JSONObject json = new JSONObject();
            try {
                json.put("email", txtUser.getText().toString());
                json.put("password", txtPasswordLogin.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AsyncRequestService asyncRequestService = new AsyncRequestService(LoginActivity.this, getString(R.string.api_login_url), SOAAPIallowedMethodsEnum.POST, json);
            asyncRequestService.execute();
            }
        });

        btnToRegister.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void showResponseMessage(JSONObject msg) {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bienvenido");
        try {
            dialog.setMessage("Success: " + msg.getString("success") + "\nMessage: " + msg.getString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.create().show();
    }
}
