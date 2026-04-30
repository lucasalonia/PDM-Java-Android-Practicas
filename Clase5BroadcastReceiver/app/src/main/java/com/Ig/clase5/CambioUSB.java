package com.Ig.clase5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*Creada a partir de new/other/BroadcastReceiver*/
public class CambioUSB extends BroadcastReceiver {


    /*Metodo de la clase padre*/
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Usamos el metodo del intent el cual devuelve un booleano*/
        boolean estado = intent.getBooleanExtra("connected",false); /*formato clave valor*/

        /*encerramos en un if el toast*/
        if(estado){
            /*Los parametros son el contexto (el cual trae la informaciion),
             segundo parametro es el mensaje que quiero mostar
            El tercer parametro es la cantiad de tiempo se muestra el mensaej*/
            Toast.makeText(context,"USB conectado",Toast.LENGTH_LONG).show();
            /*Armamos el toast y .show() par aque se vea*/
            /*El intent ya viene armado hay que saber que informacion trae.
             Va a depender a quien estoy escuchando*/

            /*Vamos hacer uso del intent para saber la informacion de la notificacion que capturamos*/
        }else{
            Toast.makeText(context,"USB desconectado",Toast.LENGTH_LONG).show();
        }

    }
}