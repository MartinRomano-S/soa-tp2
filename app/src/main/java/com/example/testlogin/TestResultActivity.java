package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testlogin.utils.Constantes;
import com.example.testlogin.utils.PhoneCaller;

public class TestResultActivity extends AppCompatActivity {

    Button botonLlamar, botonMensajear, botonVolverAHome;
    SensorManager sensorManager;
    Sensor proximitySensor;

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
                PhoneCaller.makePhoneCall(TestResultActivity.this,Constantes.TELEFONO_ATENCION_COVID);
            }
        });

        // Invoco al sensor service
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

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
                    PhoneCaller.makePhoneCall(TestResultActivity.this,Constantes.TELEFONO_ATENCION_COVID);
                }
            }
        }
    };





}