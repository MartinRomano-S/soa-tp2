package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.models.Credentials;
import com.example.testlogin.services.AsyncHttpRequest;
import com.example.testlogin.utils.BatteryReceiver;
import com.example.testlogin.utils.Configuration;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements Asyncronable<JSONObject> {

    Button btnLogin;
    Button btnToRegister;
    EditText txtUser;
    EditText txtPasswordLogin;
    Credentials credentials;
    ProgressBar prgLogin;
    private BatteryReceiver batteryReceiver = new BatteryReceiver();
    private IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

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

            credentials = new Credentials();
            credentials.setEmail(txtUser.getText().toString());
            credentials.setPassword(txtPasswordLogin.getText().toString());

            if(Configuration.isNetworkConnected(LoginActivity.this)) {
                AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(LoginActivity.this, getString(R.string.api_login_url), SOAAPIallowedMethodsEnum.POST, credentials.toJSON());
                asyncHttpRequest.execute();
            } else
                Configuration.showModalMessage(LoginActivity.this, getString(R.string.titleError), getString(R.string.networkError));
            }
        });

        addTextChangedListeners();

        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                Intent i = new Intent(LoginActivity.this, TestActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(batteryReceiver, intentFilter);
    }

    @Override
    public void showProgress(String msg) {
        prgLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        prgLogin.setVisibility(View.GONE);
    }

    @Override
    public void afterRequest(JSONObject response) {

        String msg = getString(R.string.credentialsError);
        boolean success = false;

        try {
            success = response.getBoolean("success");
        } catch (JSONException e) {
            msg = getString(R.string.requestError);
        }

        if(success) {
            Intent i = new Intent(LoginActivity.this, TwoFactorActivity.class);
            i.putExtra("email", credentials.getEmail());

            startActivity(i);
        } else {
            AlertDialog.Builder dialog;
            dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.titleError));
            dialog.setMessage(msg);
            dialog.setPositiveButton(getString(R.string.acceptButton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            dialog.create().show();
        }
    }

    private void addTextChangedListeners() {
        txtUser.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String txtValue = txtUser.getText().toString();
                Pattern p = Patterns.EMAIL_ADDRESS;
                Matcher m = p.matcher(txtValue);

                if(!m.matches())
                    txtUser.setError(getString(R.string.errorInvalidMail));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        txtPasswordLogin.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(s.length() < Configuration.MINIMUM_PASSWORD_LENGTH)
                    txtPasswordLogin.setError(getString(R.string.errorPasswordLength, Configuration.MINIMUM_PASSWORD_LENGTH));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}
