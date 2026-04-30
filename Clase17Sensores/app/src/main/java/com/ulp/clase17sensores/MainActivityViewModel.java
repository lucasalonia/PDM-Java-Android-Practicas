package com.ulp.clase17sensores;

import android.app.Application;
import static android.content.Context.*;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableLectura;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getMutableLectura() {
        if (mutableLectura == null) {
            mutableLectura = new MutableLiveData<>();
        }
        return mutableLectura;
    }


    //El viewmodel sera el que controla los valores del sensor
    public void leerSensor() {

        //Accedemos a sensor manager par aobtener la lista de sensores
        SensorManager sm = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);

        List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ALL);

        //Para evitar sobre cargar la ram con pull de string hay que utilizar un stringbuilding
        //o un string buffer. Es mas eficiente.
        StringBuilder sb = new StringBuilder();

        //Debemos recorrer la lista de sensores para saber cual es el que necesitamos
        for (Sensor sensor : sensores) {
            //usamos el metodo append para concatenar los nombres de los sensores en un
            //unico string.
            sb.append(sensor.getName());
            sb.append("\n");
        }
        //Hay que notificar al view que los datos han cambiado y parsear el builder a string
        mutableLectura.setValue(sb.toString());
    }

    public void leerUnSensor(){
        //Accedemos a sensor manager par aobtener la lista de sensores
        SensorManager sm = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);

        //Ahora accedemos a uno en especifico
        List<Sensor> sensores = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);

        //Puede que el dispositivo NO tenga el sensor por lo que preguntamos
        if (sensores.size()!=0){
            //obtenemos la primera posicion ya que es el unico objeto en la lista
            Sensor acelerometro = sensores.get(0);

            //El sensor manager debe debe registrar el Listener del sensor
            //Debe resivir la clase anonima que se encarga de manejar eventos
            //Segundo a que sensor queremos escuchar
            //Tercero a que frecuencia de lectura queremos escuchar
            sm.registerListener(new ManejaEventos(),acelerometro,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }
    //Creamos una clase interna que maneja eventos e implementa SensorEventListener.
    //Necesitamos utilizar los metodos
    private class ManejaEventos implements SensorEventListener {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            //Este metodo nos interesa
            //Guardamos los valores de lectura del sensor en variables
            //0 eje X. 1 eje Y. 2 eje Z.
           float x = event.values[0];
           float y = event.values[1];
           float z = event.values[2];
           String dato = "X: "+x+" Y: "+y+" Z: "+z;

           //le seteamos el valor al mutable para que lo muestr
            mutableLectura.setValue(dato);
        }
    }
}
