package com.example.testlogin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.services.AsyncMailSending;
import com.example.testlogin.utils.Configuration;

import org.json.JSONObject;

/**
 * EXPERIMENTAL
 *
 * Funciona el envío de SMS pero se duplican ya que tienen como SOURCE el mismo numero
 */
public class TwoFactorActivity extends AppCompatActivity implements Asyncronable<String> {

    Button btnCancel;
    Button btnResendVerificationCode;
    TextView txtLblVerificationCode;
    ProgressBar pgbVerify;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twofactor);

        btnCancel = findViewById(R.id.btnCancelVerification);
        txtLblVerificationCode = findViewById(R.id.lblVerificationCodeSent);
        btnResendVerificationCode = findViewById(R.id.btnResendVerification);
        pgbVerify = findViewById(R.id.pgbVerify);

        Intent i = getIntent();
        email = i.getStringExtra("email");

        txtLblVerificationCode.setText(getString(R.string.lblVerificationCodeSent, email));

        sendEmailVerificationCode(email);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnResendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailVerificationCode(email);
            }
        });
    }

    public void sendSMSVerificationCode() {
        if(!Configuration.checkPermission(this, Manifest.permission.SEND_SMS))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 1);

        if(Configuration.checkPermission(this, Manifest.permission.SEND_SMS)) {
            String messageToSend = Configuration.generateRandomCode();
            String number = "5491157582119";

            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
        }
    }

    public void sendEmailVerificationCode(String email) {

        AsyncMailSending asyncMailSending = new AsyncMailSending(this, email, getString(R.string.verificationCodeMailSubject), getString(R.string.verificationCodeMailBody, Configuration.generateRandomCode()));
        asyncMailSending.execute();
    }

    @Override
    public void showProgress(String msg) {
        pgbVerify.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbVerify.setVisibility(View.GONE);
    }

    @Override
    public void afterRequest(String response) {

    }
}
