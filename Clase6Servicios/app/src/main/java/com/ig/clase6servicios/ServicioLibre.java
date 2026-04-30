package com.ig.clase6servicios;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class ServicioLibre extends Service {
    /*Creamos variable atributo mediaPlayer*/
    private MediaPlayer mp;
    public ServicioLibre() {
    }
    /*La variable atributo no se instancian en el contructor sino que lo hacen en el onCreate*/
    @Override
    public void onCreate() {
        super.onCreate();

        /*Necesita el contexto y el recurso. Lo buscamos en el directorio res*/
        mp = MediaPlayer.create(this, R.raw.roxette_1_1);
    }

    /*Metodo sobreescrito de la clase padre Service que llevara
        los procesos del servicios.
        Recibe un intent, un estado y un id.
        Procede como una FUNCION y no como un PROCEDIMIENTO ya que devuelve un entero*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Los procesos suceden aca dentro por eso media player inicia aca
        mp.start();

        /*EMULAMOS UNA TAREA PESADA. Computo, busqueda en la api, lectura gps*/
        /*Necesitamos generar el contexto de ejecucion paralelos creadno hilos*/
        /*Creamos una clase anonima. Un objeto de tipo Thread con un clase Thread esécfica = POLIMORFIMSO*/
        /*Crea un hilo paralelo y hace la tarea pesada*/
        /*
        Thread trabajador = new Thread( ){
            public void run(){
                for(int i=0; i < 10; i++){
                    try {
                        Thread.sleep(10000);
                    }catch (InterruptedException e){

                    }
                    //Se despierta y realiza esta accion
                    Toast.makeText(ServicioLibre.this, "Pasó", Toast.LENGTH_SHORT).show();
                }
            }
        };
        trabajador.start();
        * Comentamos el thread para hacer otra actividad y que este quede de ejemplo/





       /*si llega hasta el final tiene que devolver
       un entero que indique el exito de la funcion. Devolvera el entero guardado en una constante*/
        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Cuando el servicio termino su tarea
        mp.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }
}