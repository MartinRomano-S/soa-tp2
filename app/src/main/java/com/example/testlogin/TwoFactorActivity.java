package com.example.testlogin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testlogin.services.JavaMailAPI;
import com.example.testlogin.utils.Configuration;

/**
 * EXPERIMENTAL
 *
 * Funciona el envío de SMS pero se duplican ya que tienen como SOURCE el mismo numero
 */
public class TwoFactorActivity extends AppCompatActivity {

    Button btnCancel;
    TextView txtLblVerificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twofactor);

        btnCancel = findViewById(R.id.btnCancelVerification);
        txtLblVerificationCode = findViewById(R.id.lblVerificationCodeSent);

        Intent i = getIntent();
        String email = i.getStringExtra("email");

        txtLblVerificationCode.setText(getString(R.string.lblVerificationCodeSent, email));

        sendEmailVerificationCode(email);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, getString(R.string.verificationCodeMailSubject), getString(R.string.verificationCodeMailBody, Configuration.generateRandomCode()));
        javaMailAPI.execute();
    }
}
