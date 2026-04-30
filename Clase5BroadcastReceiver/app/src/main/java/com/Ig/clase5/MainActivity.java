package com.Ig.clase5;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    /*La clase que extiende de broadcast, llamada CambioUSB, la usaremos en esta activity dentro del flujo de la aplicacion*/
    /*Creamos un atributo con la clase*/
    private CambioUSB cambioUSB;
    /*El objeto nunca se instancia antes de usarlo*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Instansiamos la clase en la creacion*/
        /*Lo creamos en el onCreat porque lo vamos usar siempre. Pero aun asi no se hace fuera de los metodos*/
        cambioUSB = new CambioUSB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    //Lo ponemos aca para que se reduzca el consumo y solo
    // escuche cuando el usuario este interactuando con la app
    protected void onResume() {
        super.onResume();
        /*Le tengo que decir al receiver con que notificacion se tiene que registrar*/
        /*Primer parameto es el objeto creado.
        * El segundo es un intent filter, el cual le avisara al sistema operativo que mi boradcast es
        *  quien escuchaun determinado evento*/
        registerReceiver(cambioUSB, new IntentFilter("android.hardware.usb.action.USB_STATE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*Desregistramos el metodo*/
        unregisterReceiver(cambioUSB);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*Desregistramos el metodo*/
        unregisterReceiver(cambioUSB);
    }
}