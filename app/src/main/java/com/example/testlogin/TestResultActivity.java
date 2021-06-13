
package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testlogin.interfaces.Asyncronable;
import com.example.testlogin.models.Event;
import com.example.testlogin.services.AsyncHttpRequest;
import com.example.testlogin.services.ShakeDetector;
import com.example.testlogin.utils.Configuration;
import com.example.testlogin.utils.Constantes;
import com.example.testlogin.utils.PhoneCaller;
import com.example.testlogin.utils.SOAAPIallowedMethodsEnum;
import com.example.testlogin.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TestResultActivity extends AppCompatActivity implements Asyncronable<JSONObject> {

    Button botonLlamar, botonMensajear, botonVolverAHome;
    SensorManager sensorManager;
    Sensor proximitySensor, mAccelerometer;
    private ShakeDetector mShakeDetector;
    SharedPreferencesManager spm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        botonLlamar = findViewById(R.id.botonLlamarAtencionCovid);
        botonMensajear = findViewById(R.id.botonMensajearCovid);
        botonVolverAHome = findViewById(R.id.botonVolverAHome);

        spm = SharedPreferencesManager.getInstance(TestResultActivity.this);

        if(!Configuration.checkPermission(this, Manifest.permission.CALL_PHONE))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 1);

        if(!Configuration.checkPermission(this, Manifest.permission.SEND_SMS))
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 1);

        botonVolverAHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestResultActivity.this, HomeActivity.class));
            }
        });

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Configuration.checkPermission(TestResultActivity.this, Manifest.permission.CALL_PHONE))
                    ActivityCompat.requestPermissions(TestResultActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);

                if(Configuration.checkPermission(TestResultActivity.this, Manifest.permission.CALL_PHONE))
                    PhoneCaller.makePhoneCall(TestResultActivity.this,Constantes.TELEFONO_ATENCION_COVID);
            }
        });

        botonMensajear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    spm.sendMessageToEmergencyContactList(TestResultActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Invoco al sensor service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager != null) {
            // Desde el sensor service llamo al sensor de proximidad
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            // Si el teléfono no tiene sensor de proximidad
            if (proximitySensor == null) {
                Toast.makeText(this, "El teléfono no cuenta con sensor de proximidad.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Registro el sensor con el sensor manager
                sensorManager.registerListener(proximitySensorEventListener,
                        proximitySensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }

            // SHAKE

            // ShakeDetector initialization
            mAccelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mShakeDetector = new ShakeDetector();
            mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

                @Override
                public void onShake(int count) {
                    try {
                        SharedPreferencesManager spm = SharedPreferencesManager.getInstance(TestResultActivity.this);
                        spm.sendMessageToEmergencyContactList(TestResultActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        sensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        sensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    //Llamo a la clase sensorEventListener para detectar cambio en el estado del sensor.
    SensorEventListener proximitySensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // No hago nada
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // Chequeo si el sensor de proximidad cambió.
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {

                    Event eventInfo = new Event();
                    eventInfo.setEventDate(new Date());
                    eventInfo.setDescription("Se ha activado el sensor de proximidad.");
                    eventInfo.setType(Constantes.EVENT_TYPES.PROXIMITY.toString());

                    try {
                        spm.saveEvent(eventInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(TestResultActivity.this, getString(R.string.api_event_url), SOAAPIallowedMethodsEnum.POST, null, eventInfo.toJSON());
                    asyncHttpRequest.execute();

                    PhoneCaller.makePhoneCall(TestResultActivity.this,Constantes.TELEFONO_ATENCION_COVID);
                }
            }
        }
    };

    @Override
    public void showProgress(String msg) {}

    @Override
    public void hideProgress() {}

    @Override
    public void afterRequest(JSONObject response) {}
}