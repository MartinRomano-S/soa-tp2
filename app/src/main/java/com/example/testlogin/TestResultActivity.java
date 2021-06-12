package com.example.testlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestResultActivity extends AppCompatActivity {

    Button botonLlamar, botonMensajear, botonVolverAHome;
    private Context mContext=TestResultActivity.this;

    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        botonLlamar = findViewById(R.id.botonLlamarAtencionCovid);
        botonMensajear = findViewById(R.id.botonMensajearCovid);
        botonVolverAHome = findViewById(R.id.botonVolverAHome);

        botonVolverAHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestResultActivity.this, HomeActivity.class));
            }
        });

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1121891188"));
                //startActivity(call);

                if (Build.VERSION.SDK_INT >= 23) {
                    String[] PERMISSIONS = {android.Manifest.permission.CALL_PHONE};
                    if (!hasPermissions(mContext, PERMISSIONS)) {
                        ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
                    } else {
                        makeCall();
                    }
                } else {
                    makeCall();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    Toast.makeText(mContext, "The app was not allowed to call.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeCall()
    {
        Toast.makeText(mContext, "SE SUPONE QUE ESTOY LLAMANDO", Toast.LENGTH_LONG).show();
        String number="1121891188";
        Intent intent4=new Intent(Intent.ACTION_CALL);
        intent4.setData(Uri.parse("tel:"+number));
        startActivity(intent4);
    }

}