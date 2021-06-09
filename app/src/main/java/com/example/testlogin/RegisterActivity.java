package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.models.Credentials;
import com.example.testlogin.models.User;
import com.example.testlogin.services.AsyncHttpRequest;
import com.example.testlogin.utils.Configuration;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements Asyncronable<JSONObject> {

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

                //TODO VALIDACIÓN DE DATOS
                User user = new User();
                user.setCredentials(new Credentials(txtEmail.getText().toString(), txtPassword.getText().toString()));
                user.setDni(Integer.valueOf(txtDNI.getText().toString()));
                user.setName(txtName.getText().toString());
                user.setLastname(txtLastname.getText().toString());

                if(Configuration.isNetworkConnected(RegisterActivity.this)) {
                    AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(RegisterActivity.this, getString(R.string.api_register_url), SOAAPIallowedMethodsEnum.POST, user.toJSON());
                    asyncHttpRequest.execute();
                } else
                    Configuration.showModalMessage(RegisterActivity.this, getString(R.string.titleError), getString(R.string.networkError));
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
    public void showProgress(String msg) {
        pgbRegister.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbRegister.setVisibility(View.GONE);
    }

    @Override
    public void afterRequest(JSONObject response){

        //TODO
        //Agregar lógica para ir a login o a twoFactor. Ver manejo de token
        AlertDialog.Builder dialog;
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Bienvenido");
        dialog.setMessage("");
        try {
            dialog.setMessage("Success: " + response.getString("success") + "\nToken: " + response.getString("token"));
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
