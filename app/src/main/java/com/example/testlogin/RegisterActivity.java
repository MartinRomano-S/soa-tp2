package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testlogin.interfaces.AsyncronableRequest;
import com.example.testlogin.models.Credentials;
import com.example.testlogin.models.User;
import com.example.testlogin.services.AsyncRequestService;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements AsyncronableRequest {

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

                User user = new User();
                user.setCredentials(new Credentials(txtEmail.getText().toString(), txtPassword.getText().toString()));
                user.setDni(Integer.valueOf(txtDNI.getText().toString()));
                user.setName(txtName.getText().toString());
                user.setLastname(txtLastname.getText().toString());

                AsyncRequestService asyncRequestService = new AsyncRequestService(RegisterActivity.this, getString(R.string.api_register_url), SOAAPIallowedMethodsEnum.POST, user.toJSON());
                asyncRequestService.execute();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    @Override
    public void showResponseMessage(JSONObject msg) {
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bienvenido");
        try {
            dialog.setMessage("Success: " + msg.getString("success") + "\nToken: " + msg.getString("token"));
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

    public void afterRequest(JSONObject msg){

    }
}
