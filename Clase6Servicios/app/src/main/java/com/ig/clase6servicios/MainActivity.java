package com.ig.clase6servicios;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    /*Iniciamos el servicio en main activity primero creando
     un atributo del objeto INTENT, ya que startService
      espera un Intent y no un objeto de tipo SErvice*/
    private Intent intentQueComunicaElServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Una buena practica seria iniciarlo en onResume()*/
        /*El intent espera el contexto el cual es la MainActivity por eso es this.
        Y el servicio que va a comunicar*/
        /*La clase lleva .class porque el constructor
        de Intent no necesita una instancia del servicio,
        sino una referencia a la definición de la clase*/
        /*
        * ServicioLibre: Es el plano de construcción (la clase en sí).
        * ServicioLibre.class: Es un objeto de tipo Class<ServicioLibre>.
         Es un "token" que contiene los metadatos de la clase.*/
        intentQueComunicaElServicio = new Intent(this, ServicioLibre.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Iniciamos el servicio*/
        startService(intentQueComunicaElServicio);
    }
}