package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.testlogin.models.TestAnswers;
import com.example.testlogin.services.TestResolver;

public class TestActivity extends AppCompatActivity {

    RadioGroup grupoRespuestaOlfato, grupoRespuestaGusto, grupoRespuestaTos, grupoRespuestaDolorGarganta, grupoRespuestaDificultarRespiratoria, grupoRespuestaDolorCabeza, grupoRespuestaDiarrea, grupoRespuestaVomitos, grupoRespuestaDolorMuscular;
    Button botonEnviarTest;
    TestAnswers testAnswers;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        grupoRespuestaOlfato = findViewById(R.id.radioGroupOlfato);
        grupoRespuestaGusto = findViewById(R.id.radioGroupGusto);
        grupoRespuestaTos = findViewById(R.id.radioGroupTos);
        grupoRespuestaDolorGarganta = findViewById(R.id.radioGroupDolorGarganta);
        grupoRespuestaDificultarRespiratoria = findViewById(R.id.radioGroupDificultadRespiratoria);
        grupoRespuestaDolorCabeza = findViewById(R.id.radioGroupDolorCabeza);
        grupoRespuestaDiarrea = findViewById(R.id.radioGroupDiarrea);
        grupoRespuestaVomitos = findViewById(R.id.radioGroupVomitos);
        grupoRespuestaDolorMuscular = findViewById(R.id.radioGroupDolorMuscular);
        botonEnviarTest = findViewById(R.id.testConsultar);

        builder = new AlertDialog.Builder(this);

        botonEnviarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnswers = new TestAnswers();
                obtenerRespuestas(); //Lee las respuestas del formulario y las carga en testAnswers
                TestResolver testResolver = new TestResolver(testAnswers);
                boolean testResult=testResolver.resolve();

                if(testResult){
                    Toast.makeText(getApplicationContext(),"Acá debería llevarte a otra activity.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    builder.setMessage(R.string.sinSintomas) .setTitle(R.string.resultadoTest);
                    builder.setCancelable(false)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.volverAlTest, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.show();
                }




            }
        });
    }

    private void obtenerRespuestas(){
        int radioId;
        RadioButton respuesta;
        radioId = grupoRespuestaOlfato.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setOlfato(respuesta.getText());
        radioId = grupoRespuestaGusto.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setGusto(respuesta.getText());
        radioId = grupoRespuestaTos.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setTos(respuesta.getText());
        radioId = grupoRespuestaDolorGarganta.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setDolorGarganta(respuesta.getText());
        radioId = grupoRespuestaDificultarRespiratoria.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setDificultadRespiratoria(respuesta.getText());
        radioId = grupoRespuestaDolorCabeza.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setDolorCabeza(respuesta.getText());
        radioId = grupoRespuestaDiarrea.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setDiarrea(respuesta.getText());
        radioId = grupoRespuestaVomitos.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setVomitos(respuesta.getText());
        radioId = grupoRespuestaDolorMuscular.getCheckedRadioButtonId();
        respuesta = findViewById(radioId);
        testAnswers.setDolorMuscular(respuesta.getText());

    }
}